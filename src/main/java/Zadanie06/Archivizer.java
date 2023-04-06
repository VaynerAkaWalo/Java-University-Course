package Zadanie06;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Archivizer implements ArchivizerInterface {

    int baseDirPathLen;
    @Override
    public int compress(String dir, String filename) {
        int bufferSize = 20480;
        File file = new File(dir);
        baseDirPathLen = file.getPath().length();

        Directory baseDir = fillDirectory(file);

        LinkedList<EverythingIsAFile> queue = new LinkedList<>();
        try (OutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(filename), bufferSize), bufferSize)) {
            queue.add(baseDir);

            while (!queue.isEmpty()) {
                EverythingIsAFile f = queue.remove();
                f.write(out, file.getPath());

                if (f instanceof Directory d) {
                    queue.addAll(d.content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Path compressed = Paths.get(filename);
        int size = 0;
        try {
            size = Math.toIntExact(Files.size(compressed));
        }
        catch (Exception e) {
            System.out.println("Nie można pobrać rozmiaru pliku zkompresowanego");
        }
        return size;
    }

    @Override
    public void decompress(String filename, String dir) {
        int bufferSize = 20480;
        try (InputStream in = new BufferedInputStream(new GZIPInputStream(new FileInputStream(filename), bufferSize), bufferSize)) {
            while (readFile(in, dir, bufferSize) > 0);
        } catch (Exception e) {
            System.out.println("Nie można pobrać rozmiaru pliku zkompresowanego");
        }
    }

    private int readFile(InputStream reader, String dir, int bufferSize) throws Exception {
        byte[] buffer = new byte[bufferSize];
        if (reader.read(buffer, 0, 5) < 0) {
            return -1;
        }
        int pathLen = ByteBuffer.wrap(buffer, 1, 4).getInt();
        reader.read(buffer, 5, pathLen);
        String path = new String(buffer, 5, pathLen);

        if (buffer[0] == (byte) 68) {
            File d = new File(dir + path);
            d.mkdir();
            return 1;
        }

        if (buffer[0] == (byte) 70) {
            reader.read(buffer, 5 + pathLen, 9);
            if(buffer[5 + pathLen] != (byte)2) {
                System.out.println("Błędny bajt na pozycji bajta kontrolnego");
                throw new IOException();
            }
            long bodyLen = ByteBuffer.wrap(buffer, 5 + 1 + pathLen, 8).getLong();

            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(dir + path))) {
                long left = bodyLen;
                int toRead;
                while (left > 0) {
                    if (left > bufferSize)
                        toRead = bufferSize;
                    else
                        toRead = Math.toIntExact(left);

                    reader.read(buffer, 0, toRead);
                    out.write(buffer, 0, toRead);
                    left -= toRead;
                }
            } catch (Exception e) {
                System.out.println("Błąd podczas czytania pliku");
                throw new IOException();
            }
            return 1;
        }
        return 0;
    }

    private Directory fillDirectory(File file) {
        Directory directory = new Directory(file.getName(), file.getPath().substring(baseDirPathLen));

        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory())
                directory.addDir(f);
            else {
                directory.addFile(f, baseDirPathLen);
            }
        }
        return directory;
    }


    abstract class EverythingIsAFile {
        String name;
        String path;

        public EverythingIsAFile(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public void writeHeader(OutputStream writer, byte type) throws IOException {
            ArrayList<Byte> result = new ArrayList<>();
            result.add(type);

            byte[] pathSize = ByteBuffer.allocate(4).putInt(path.getBytes().length).array();
            for (byte b : pathSize) {
                result.add(b);
            }
            byte[] bytes = path.getBytes();
            for (byte b : bytes) {
                result.add(b);
            }

            for (byte b : result) {
                writer.write(b);
            }
        }

        abstract public void write(OutputStream writer, String baseDirPath) throws IOException;

    }

    class Directory extends EverythingIsAFile {
        ArrayList<EverythingIsAFile> content = new ArrayList<>();

        public Directory(String name, String path) {
            super(name, path);
        }

        void addDir(File file) {
            content.add(fillDirectory(file));
        }

        void addFile(File file, int offset) {
            content.add(new FileWithBody(file.getName(), file.getPath().substring(offset)));
        }

        public void write(OutputStream writer, String baseDirPath) throws IOException {
            writeHeader(writer, (byte) 68);
        }

    }

    class FileWithBody extends EverythingIsAFile {
        public FileWithBody(String name, String path) {
            super(name, path);
        }

        public void write(OutputStream writer, String baseDirPath) throws IOException {
            writeHeader(writer, (byte) 70);

            Path file = Paths.get(baseDirPath + path);
            byte[] fileSize = ByteBuffer.allocate(8).putLong(Files.size(file)).array();
            writer.write((byte)2);
            writer.write(fileSize, 0, 8);
            try (InputStream in = new BufferedInputStream(new FileInputStream(baseDirPath + path), 20480)) {
                in.transferTo(writer);
            }
        }
    }
}
