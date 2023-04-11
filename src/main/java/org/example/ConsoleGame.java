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
            boolean canHit = true;
            if (!capture) {
                canHit = board.canHit(isBlackTurn);
            }
            if(board.canMove(fromRow, fromCol, toRow, toCol, capture) && canHit) {
                System.out.println("Проверка кэн мув");
                board.moveChecker(fromRow, fromCol, toRow, toCol);
                if(capture && board.hasCaptureMoves(isBlackTurn)) {
                    System.out.println("Есть возможность бить, доп. ход");
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
        System.out.print("   0  1  2  3   4  5  6   7\n");

        for(int row = 0; row < 8; row++) {
            System.out.print(row + " ");

            for(int col = 0; col < 8; col++) {
                Checker checker = board.getChecker(row, col);
                System.out.print(checker.getPicture() + " ");
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
                if((!checker.getPicture().equals("⬛️") || !checker.getPicture().equals("⬜️")) && checker.isBlack()) {
                    if(board.canMove(row, col, Math.min((row + 1), 7), Math.min((col + 1), 7), false) ||
                            board.canMove(row, col, Math.min((row + 1), 7), (col - 1) < 0 ? 1 : (col - 1), false) ||
                            board.canMove(row, col, Math.min((row + 2), 7), Math.min((col + 2), 7), true) ||
                            board.canMove(row, col, Math.min((row + 2), 7), Math.max((col - 2), 0), true)) {
                        blackHasMoves = true;
                    }
                }
                if((!checker.getPicture().equals("⬛️") || !checker.getPicture().equals("⬜️")) && !checker.isBlack()) {
                    if(board.canMove(row, col, Math.max((row - 1), 0), Math.min((col + 1), 7), false) ||
                            board.canMove(row, col, Math.min((row + 1), 7), (col - 1) < 0 ? 1 : (col - 1), false) ||
                            board.canMove(row, col, Math.min((row + 2), 7), Math.min((col + 2), 7), true) ||
                            board.canMove(row, col, Math.max((row - 2), 0), Math.max((col - 2), 0), true)) {
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
