package Zadanie10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector implements NetConnection{

    /**
     *
     * @param host adres IP lub nazwa komputera
     * @param port numer portu, na którym serwer oczekuje na począczenie
     */
    @Override
    public void connect(String host, int port) {
        Socket socket = null;
        BufferedReader bufferedReader = null;

        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Program");
            printWriter.flush();
            String line;
            int nLine = 0;
            int number = 0;
            int count = 0;
            boolean end = false;
            while((line = bufferedReader.readLine()) != null) {
                if(nLine == 3) {
                    number = Integer.parseInt(line.split(": ")[1]);
                }
                if(nLine > 3 && !end){
                    if(line.equals("EOD")) {
                        printWriter.println(count);
                        printWriter.flush();
                        end = true;

                    }
                    else {
                        int readNumber = Integer.parseInt(line);
                        if(readNumber == number) {
                            count++;
                        }
                    }
                }
                if(end) {
                    System.out.println(line);
                }
                nLine++;
            }

        } catch (UnknownHostException e) {
            System.out.println( "Nie znam takiego hosta " + host );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
