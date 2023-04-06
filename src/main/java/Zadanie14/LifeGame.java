package Zadanie14;

import java.util.Arrays;

public class LifeGame implements LifeGameInterface{
    boolean[][] board;

    @Override
    public void set(boolean[][] board) {
        this.board = board;
        newBoard();
    }

    @Override
    public void oneStep(int minToSurvive, int maxToSurvive, int minToBorn, int maxToBorn) {
        boolean[][] tempboard = copyBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int neighbours = numberOfNeighbours(i, j);
                if(board[i][j]) {
                    if(neighbours > maxToSurvive || neighbours < minToSurvive) {
                        tempboard[i][j] = false;
                    }
                }
                else {
                    if(neighbours >= minToBorn && neighbours <= maxToBorn) {
                        tempboard[i][j] = true;
                    }
                }
            }
        }
        board = tempboard;
        newBoard();
    }

    @Override
    public boolean[][] get() {
        return board;
    }

    public int numberOfNeighbours(int x, int y) {
        int minX = Math.max(x - 1, 0);
        int maxX = Math.min(board.length, x + 2);
        int minY = Math.max(y - 1, 0);
        int maxY = Math.min(board[0].length, y + 2);


        int counter = 0;
        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                if(i != x || j != y) {
                    if (board[i][j]) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
    private boolean[][] copyBoard() {
        return Arrays.stream(board).map(boolean[]::clone).toArray(boolean[][]::new);
    }
    private void newBoard() {
        CellBounds bounds = getBounds();
        boolean[][] tempBoard = new boolean[bounds.maxX() - bounds.minX() + 3][bounds.maxY() - bounds.minY() + 3];
        for (int i = bounds.minX(); i < bounds.maxX() + 1; i++) {
            for (int j = bounds.minY(); j < bounds.maxY() + 1; j++) {
                tempBoard[i - bounds.minX() + 1][j - bounds.minY() + 1] = board[i][j];
            }
        }

        board = tempBoard;
    }

    private CellBounds getBounds() {
        int minX = board.length;
        int minY = board[0].length;
        int maxX = 0;
        int maxY = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j]) {
                    if(minX > i) {
                        minX = i;
                    }
                    if(minY > j) {
                        minY = j;
                    }
                    if(maxX < i) {
                        maxX = i;
                    }
                    if(maxY < j) {
                        maxY = j;
                    }
                }
            }
        }
        return new CellBounds(minX, minY, maxX, maxY);
    }

}

record CellBounds(int minX, int minY, int maxX, int maxY) {}
