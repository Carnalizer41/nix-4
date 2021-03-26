package ua.com.shyrkov.level3.life;

import java.util.Random;

public class LifeGame {

    private final int columnsAmount;
    private final int rowsAmount;
    private LifeCell[][] board;

    public LifeGame(int columnsAmount, int rowsAmount) {
        this.columnsAmount = columnsAmount;
        this.rowsAmount = rowsAmount;
        this.board = new LifeCell[columnsAmount][rowsAmount];
        initRandomBoardCells();
        printBoard(board);
    }

    private void initRandomBoardCells() {
        Random random = new Random();
        for (int i = 0; i < columnsAmount; i++) {
            for (int j = 0; j < rowsAmount; j++) {
                board[i][j] = new LifeCell(random.nextBoolean());
            }
        }
    }

    public void calculateNextGeneration() {
        LifeCell[][] nextGenBoard = new LifeCell[columnsAmount][rowsAmount];
        for (int i = 0; i < columnsAmount; i++) {
            for (int j = 0; j < rowsAmount; j++) {
                int liveNeighbors = liveNeighbors(columnsAmount, rowsAmount, i, j);
                if (board[i][j].isAlive()) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGenBoard[i][j] = new LifeCell(false);
                    } else
                        nextGenBoard[i][j] = new LifeCell(true);
                } else {
                    if (liveNeighbors == 3) {
                        nextGenBoard[i][j] = new LifeCell(true);
                    } else nextGenBoard[i][j] = new LifeCell(false);
                }
            }
        }
        printBoard(nextGenBoard);
        board = nextGenBoard;
    }

    private int liveNeighbors(int m, int n, int i, int j) {
        int lives = 0;
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                if (board[x][y].isAlive())
                    lives++;
            }
        }
        if (board[i][j].isAlive())
            lives--;
        return lives;
    }

    private void printBoard(LifeCell[][] board) {
        for (LifeCell[] lifeCells : board) {
            for (LifeCell lifeCell : lifeCells) {
                System.out.print("[" + lifeCell.toString() + "] ");
            }
            System.out.println();
        }
    }
}
