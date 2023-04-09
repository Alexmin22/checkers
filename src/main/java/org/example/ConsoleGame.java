package org.example;

import java.util.Scanner;

public class ConsoleGame {
    private Board board;
    private boolean isBlackTurn;
    private Scanner scanner;

    public ConsoleGame() {
        board = new Board();
        isBlackTurn = true;
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Checkers!");

        while(true) {
            printBoard();
            if(isBlackTurn) {
                System.out.println("Black's turn");
            } else {
                System.out.println("White's turn");
            }
            System.out.println("Enter coordinates of checker you want to move:");
            int fromRow = scanner.nextInt();
            int fromCol = scanner.nextInt();
            System.out.println("Enter coordinates of where you want to move the checker:");
            int toRow = scanner.nextInt();
            int toCol = scanner.nextInt();
            boolean capture = Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2;
            if(board.canMove(fromRow, fromCol, toRow, toCol, capture)) {
                board.moveChecker(fromRow, fromCol, toRow, toCol);
                if(capture && board.hasCaptureMoves(isBlackTurn)) {
                    System.out.println("You can capture again!");
                } else {
                    isBlackTurn = !isBlackTurn;
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
            if(isGameOver()) {
                printBoard();
                System.out.println("Game over!");
                break;
            }
        }
    }

    private void printBoard() {
        System.out.print("  ");
        for(int col = 0; col < 8; col++) {
            System.out.print(col + " ");
        }
        System.out.println();
        for(int row = 0; row < 8; row++) {
            System.out.print(row + " ");
            for(int col = 0; col < 8; col++) {
                Checker checker = board.getChecker(row, col);
                if(checker != null) {
                    System.out.print(checker.getPicture() + " ");
                } else if((row + col) % 2 == 0) {
                    System.out.print(Board.WHITE + " ");
                } else {
                    System.out.print(Board.IS_EMPTY + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean isGameOver() {
        boolean blackHasMoves = false;
        boolean whiteHasMoves = false;
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                Checker checker = board.getChecker(row, col);
                if(checker != null && checker.isBlack()) {
                    if(board.canMove(row, col, row + 1, col + 1, false) ||
                            board.canMove(row, col, row + 1, col - 1, false) ||
                            board.canMove(row, col, row + 2, col + 2, true) ||
                            board.canMove(row, col, row + 2, col - 2, true)) {
                        blackHasMoves = true;
                    }
                }
                if(checker != null && !checker.isBlack()) {
                    if(board.canMove(row, col, row + 1, col + 1, false) ||
                            board.canMove(row, col, row + 1, col - 1, false) ||
                            board.canMove(row, col, row - 2, col + 2, true) ||
                            board.canMove(row, col, row - 2, col - 2, true)) {
                        whiteHasMoves = true;
                    }
                }
            }
        }
        if(!blackHasMoves || !whiteHasMoves) {
            return true;
        }
        return false;
    }
}
