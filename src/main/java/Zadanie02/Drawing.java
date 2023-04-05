package Zadanie02;

public class Drawing implements SimpleDrawing{

    private int[][] canvas = null;
    private int size = 0;
    private int drawStartX = 0;
    private int drawStartY = 0;

    @Override
    public void setCanvasGeometry(Geometry input) {
        size = input.getSize();
        canvas = new int[size][size];
        drawStartX = input.getInitialFirstCoordinate();
        drawStartY = input.getInitialSecondCoordinate();
    }

    @Override
    public void draw(Segment segment) {
        int endIndex;

        if(segment.getDirection() == 1) {

            endIndex = Math.min(drawStartX + segment.getLength() - 1, size - 1);
            for(int i = drawStartX; i < endIndex + 1; i++)
                canvas[i][drawStartY] = segment.getColor();
            drawStartX = endIndex;
        } else if (segment.getDirection() == 2) {

            endIndex = Math.min(drawStartY + segment.getLength() - 1, size - 1);
            for(int i = drawStartY; i < endIndex + 1; i++)
                canvas[drawStartX][i] = segment.getColor();
            drawStartY = endIndex;
        } else if (segment.getDirection() == -1) {

            endIndex = Math.max(drawStartX - segment.getLength() + 1, 0);
            for(int i = drawStartX; i >= endIndex; i--)
                canvas[i][drawStartY] = segment.getColor();
            drawStartX = endIndex;
        } else if (segment.getDirection() == -2) {

            endIndex = Math.max(drawStartY - segment.getLength() + 1, 0);
            for(int i = drawStartY; i >= endIndex; i--)
                canvas[drawStartX][i] = segment.getColor();
            drawStartY = endIndex;
        }
    }

    @Override
    public int[][] getPainting() {
        return canvas;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
                canvas[i][j] = 0;
        }
    }
}
