package Zadanie02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawingTest {

    SimpleDrawing drawing;
    int size = 5;

    @BeforeEach
    void setUp() {
        drawing = new Drawing();
        drawing.setCanvasGeometry(new TestGeometry(size, 0, 0));
    }

    @Test
    void withoutInputShouldReturnClearCanvas() {
        int[][] result = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
        };

        assertArrayEquals(result, drawing.getPainting());
    }

    @Test
    void singleLine() {
        Segment line1 = new TestSegment(1, 5, 1);
        drawing.draw(line1);

        int[][] result = {
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
        };

        assertArrayEquals(result, drawing.getPainting());
    }

    @Test
    void twoLines() {
        Segment[] lines = {
                new TestSegment(1, 5, 1),
                new TestSegment(2, 5, 2)
        };

        for (Segment line : lines) {
            drawing.draw(line);
        }

        int[][] result = {
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {2, 2, 2, 2, 2},
        };

        assertArrayEquals(result, drawing.getPainting());
    }

    @Test
    void overlapingLine() {
        Segment[] lines = {
                new TestSegment(1, 5, 1),
                new TestSegment(-1, 5, 2)
        };

        for (Segment line : lines) {
            drawing.draw(line);
        }

        int[][] result = {
                {2, 0, 0, 0, 0},
                {2, 0, 0, 0, 0},
                {2, 0, 0, 0, 0},
                {2, 0, 0, 0, 0},
                {2, 0, 0, 0, 0},
        };

        assertArrayEquals(result, drawing.getPainting());
    }

    @Test
    void advancedDrawing() {
        Segment[] lines = {
                new TestSegment(1, 5, 1),
                new TestSegment(2, 5, 2),
                new TestSegment(-1, 5, 3),
                new TestSegment(-2, 4, 4),
                new TestSegment(1, 4, 5),
                new TestSegment(2, 3, 6),
                new TestSegment(-1, 3, 7),
                new TestSegment(-2, 2, 8),
                new TestSegment(1, 2, 9),
        };

        for (Segment line : lines) {
            drawing.draw(line);
        }

        int[][] result = {
                {1, 5, 4, 4, 4},
                {1, 5, 9, 8, 3},
                {1, 5, 9, 7, 3},
                {1, 6, 6, 7, 3},
                {2, 2, 2, 2, 3},
        };

        assertArrayEquals(result, drawing.getPainting());
    }
}

class TestGeometry implements Geometry {

    private int size;
    private int firstCoordinate;

    private int secondCoordinate;

    public TestGeometry(int size, int firstCoordinate, int secondCoordinate) {
        this.size = size;
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getInitialFirstCoordinate() {
        return firstCoordinate;
    }

    @Override
    public int getInitialSecondCoordinate() {
        return secondCoordinate;
    }
}

class TestSegment implements Segment {

    private int direction;
    private int length;
    private int color;

    public TestSegment(int direction, int length, int color) {
        this.direction = direction;
        this.length = length;
        this.color = color;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getColor() {
        return color;
    }
}