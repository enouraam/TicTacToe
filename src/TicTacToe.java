import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        int moveXrow; // X row input
        int moveXcol; // X col input
        int moveOrow; // O row input
        int moveOcol; // O col input
        Scanner in = new Scanner(System.in);
        boolean keepPlaying = true;

        while (keepPlaying) {
            boolean playing = true;
            boolean playerMove = true;
            clearBoard();
            display();

            do {
                boolean validMoveConfirm = false;

                if (playerMove) {
                    System.out.println();
                    moveXrow = SafeInput.getRangedInt(in, "Player X choose row: ", 1, 3) - 1;
                    moveXcol = SafeInput.getRangedInt(in, "Player X choose col: ", 1, 3) - 1;

                    do {
                        if (isValidMove(moveXrow, moveXcol)) {
                            board[moveXrow][moveXcol] = "X";
                            validMoveConfirm = true;
                        } else {
                            moveXrow = SafeInput.getRangedInt(in, "Player X choose row: ", 1, 3) - 1;
                            moveXcol = SafeInput.getRangedInt(in, "Player X choose col: ", 1, 3) - 1;
                        }
                    } while (!validMoveConfirm);

                    display();
                    playerMove = false;

                } else {
                    moveOrow = SafeInput.getRangedInt(in, "Player O choose row: ", 1, 3) - 1;
                    moveOcol = SafeInput.getRangedInt(in, "Player O choose col: ", 1, 3) - 1;

                    do {
                        if (isValidMove(moveOrow, moveOcol)) {
                            board[moveOrow][moveOcol] = "O";
                            validMoveConfirm = true;
                        } else {
                            moveOrow = SafeInput.getRangedInt(in, "Player O choose row: ", 1, 3) - 1;
                            moveOcol = SafeInput.getRangedInt(in, "Player O choose col: ", 1, 3) - 1;
                        }
                    } while (!validMoveConfirm);

                    display();
                    playerMove = true;
                }

                if (isWin("X") || isWin("O") || isTie()) {
                    playing = false;
                }

            } while (playing);

            keepPlaying = SafeInput.getYNConfirm(in, "\nWould you like to play again Y/N?: ");
        }
    }

    private static void clearBoard() {
        for (String[] row : board) {
            Arrays.fill(row, "_");
        }
    }

    private static void display() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    private static boolean isValidMove(int row, int col) {
        if ("X".equals(board[row][col]) || "O".equals(board[row][col])) {
            System.out.println("The position is taken. Try again.");
            return false;
        }
        return true;
    }

    private static boolean isWin(String player) {
        if (isColWin(player)) {
            System.out.printf("Player %s won via column", player);
            return true;
        } else if (isRowWin(player)) {
            System.out.printf("Player %s won via row", player);
            return true;
        } else if (isDiagonalWin(player)) {
            System.out.printf("Player %s won diagonally", player);
            return true;
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player) {
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        } else if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private static boolean isTie() {
        for (int[][] vector : WIN_VECTORS) {
            boolean hasX = false;
            boolean hasO = false;

            for (int[] pos : vector) {
                int row = pos[0];
                int col = pos[1];
                if (board[row][col].equals("X")) {
                    hasX = true;
                } else if (board[row][col].equals("O")) {
                    hasO = true;
                }
            }

            if (!(hasX && hasO)) {
                return false;
            }
        }

        System.out.println("It's a tie!");
        return true;
    }

    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];
    private static final int[][][] WIN_VECTORS = {
            {{0, 0}, {0, 1}, {0, 2}},  // Top row
            {{1, 0}, {1, 1}, {1, 2}},  // Middle row
            {{2, 0}, {2, 1}, {2, 2}},  // Bottom row
            {{0, 0}, {1, 0}, {2, 0}},  // Left column
            {{0, 1}, {1, 1}, {2, 1}},  // Middle column
            {{0, 2}, {1, 2}, {2, 2}},  // Right column
            {{0, 0}, {1, 1}, {2, 2}},  // Diagonal from top-left to bottom-right
            {{0, 2}, {1, 1}, {2, 0}}   // Diagonal from top-right to bottom-left
    };
}