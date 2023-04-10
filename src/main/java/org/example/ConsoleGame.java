package org.example;

import java.util.Scanner;

import static org.example.Board.EMPTY;

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
                System.out.println("Черные ходят");
            } else {
                System.out.println("Белые ходят");
            }
            System.out.println("Введите координаты шашки, которой хотите ходить (сначала строка, потом колонка):");
            int fromRow = scanner.nextInt();
            int fromCol = scanner.nextInt();
            System.out.println("Координаты куда делаем ход (сначала строка, потом колонка):");
            int toRow = scanner.nextInt();
            int toCol = scanner.nextInt();
            boolean capture = Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2;
            if(board.canMove(fromRow, fromCol, toRow, toCol, capture)) {
                board.moveChecker(fromRow, fromCol, toRow, toCol);
                if(capture && board.hasCaptureMoves(isBlackTurn)) {
                    System.out.println("Вы должны бить!");
                } else {
                    System.out.println("Блэк тёрн");
                    isBlackTurn = !isBlackTurn;
                }
            } else {
                System.out.println("Недопустимый ход!");
            }
            if(isGameOver()) {
                printBoard();
                System.out.println("Game over!");
                break;
            }
        }
    }

    private void printBoard() {
        System.out.print("   ");
        for(int col = 0; col < 8; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();
        for(int row = 0; row < 8; row++) {
            System.out.print(row + " ");
            for(int col = 0; col < 8; col++) {
//                Checker checker = board.getChecker(row, col);
                 if((row + col) % 2 == 0 && row > 4) {
                    System.out.print(Board.WHITE + " ");
                } else if((row + col) % 2 == 0 && row < 3) {
                    System.out.print(Board.BLACK + " ");
                } else if((row + col) % 2 == 0 && (row == 3 || row == 4)) {
                     System.out.print(EMPTY + " ");
                 } else {
                     System.out.print(Board.WHITE_SQUARE + " ");
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
