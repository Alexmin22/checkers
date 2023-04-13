package org.example;

public class Board {

    public static final String BLACK = "⚫️";
    public static final String WHITE = "⚪️";
    public static final String WHITE_SQUARE = "⬜️";
    public static final String EMPTY = "⬛️";
    public static final String KING_BLACK = "♔";
    public static final String KING_WHITE = "♛";
    private Checker[][] board;

    public Board() {
        //Инициализация доски
        board = new Checker[8][8];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (row < 3) {
                    board[row][col] = new Checker(row, col, true,
                            (row + col) % 2 == 0 ? BLACK : WHITE_SQUARE);
                } else if (row > 4) {
                    board[row][col] = new Checker(row, col, false,
                            (row + col) % 2 == 0 ? WHITE : WHITE_SQUARE);
                } else {
                    board[row][col] = new Checker(row, col, false,
                            (row + col) % 2 == 0 ? EMPTY : WHITE_SQUARE);
                }

                //сразу помечаю пустые клетки
                if (board[row][col].getPicture().equals(EMPTY)
                        || board[row][col].getPicture().equals(WHITE_SQUARE)) {
                    board[row][col].makeEmptyCell();
                }
            }
        }
    }

    //проверка перед ходом на возможность бить шашку соперника
    public boolean canHit(boolean blackTurn) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                Checker checker = board[row][col];

                //проверяю по цвету кто сейчас ходит(если ходит черные, то смотрим есть ли белая шашка под боем)
                if (checker.isBlack() == blackTurn && !checker.isEmpty()) {

                    int[][] delta = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
                    for (int[] a : delta) {
                        int r = row + a[0];
                        int c = col + a[1];

                        //особая логика для дамки
                        if (checker.isKing()) {

                            while (r > 0 && r < 7 && c > 0 && c < 7) {
                                //нашел соперника, ищу пустую клетку за ним
                                if (!board[r][c].getPicture().equals(checker.getPicture())
                                        && !board[r][c].isEmpty()) {
                                    int jumpRow = r + a[0];
                                    int jumpCol = c + a[1];
                                    //нашел пустую клетку
                                    if (jumpRow >= 0 && jumpRow < 8 && jumpCol >= 0 && jumpCol < 8
                                            && board[jumpRow][jumpCol].getPicture().equals(EMPTY)) {
                                        System.out.println("Вам необходимо бить!");
                                        return false;
                                    }
                                    //не нашел пустой клетки за соперником
                                    break;
                                }
                            }
                        }

                        //простые шашки
                        if (r > 0 && r < 7 && c > 0 && c < 7) {

                            if (!board[r][c].isBlack() == checker.isBlack() && !board[r][c].isEmpty()) {

                                System.out.println(board[r][c] + " нашел соперника, ищу пустую клетку за ним");
                                //нашел соперника, ищу пустую клетку за ним
                                if (!board[r][c].getPicture().equals(checker.getPicture())
                                        && !board[r][c].isEmpty()) {
                                    int jumpRow = r + a[0];
                                    int jumpCol = c + a[1];
                                    //нашел пустую клетку
                                    if (jumpRow >= 0 && jumpRow < 8 && jumpCol >= 0 && jumpCol < 8
                                            && board[jumpRow][jumpCol].getPicture().equals(EMPTY)) {
                                        System.out.println(board[jumpRow][jumpCol] + " нашел пустую клетку");
                                        System.out.println("Вам необходимо бить!");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    //Проверка возможности хода
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol, boolean capture, boolean isBlackTurn) {
        Checker checker = board[fromRow][fromCol];

        System.out.println(checker.toString() + " from " + board[toRow][toCol].toString() + " to ");
        if (board[toRow][toCol].getPicture().equals(EMPTY) && checker.isBlack() == isBlackTurn) {

            if (capture) {
                System.out.println("капча");
                int rowDiff = toRow - fromRow;
                int colDiff = toCol - fromCol;
                int midRow = fromRow + rowDiff / 2;
                int midCol = fromCol + colDiff / 2;
                Checker midChecker = board[midRow][midCol];
                if (midChecker.isEmpty() || midChecker.isBlack() == checker.isBlack()) {
                    System.out.println(" if (midChecker.getPicture().equals(IS_EMPTY) ||");
                    return false;
                } else {
                    return true;
                }
            } else {
                System.out.println("Не капча");
                int rowDiff = Math.abs(toRow - fromRow);
                int colDiff = Math.abs(toCol - fromCol);
                if (checker.isKing() && rowDiff == colDiff) {
                    return true;
                } else if (!checker.isKing() && ((checker.isBlack() && toRow > fromRow && rowDiff == 1 && colDiff == 1)
                        || (!checker.isBlack() && toRow < fromRow && rowDiff == 1 && colDiff == 1))) {
                    System.out.println("не король");
                    return true;
                }
            }
        }
        System.out.println("конец метода кен мув");
        return false;
    }

    //Перемещение шашки на указанные координаты
    public void moveChecker(int fromRow, int fromCol, int toRow, int toCol, boolean capture) {

        Checker checker = board[fromRow][fromCol];
        Checker emptyCell = board[toRow][toCol];

        checker.move(toRow, toCol);
        emptyCell.move(fromRow, fromCol);

        if (toRow == 0 && !checker.isKing() && !checker.isBlack()) {
            checker.setPicture(KING_WHITE);
            checker.makeKing();
        } else if (toRow == board.length - 1 && !checker.isKing() && checker.isBlack()) {
            checker.setPicture(KING_BLACK);
            checker.makeKing();
        }
        board[toRow][toCol] = checker;
        board[fromRow][fromCol] = emptyCell;
        if (capture) {
            int rowDiff = toRow - fromRow;
            int colDiff = toCol - fromCol;
            int midRow = fromRow + rowDiff / 2;
            int midCol = fromCol + colDiff / 2;

            board[midRow][midCol].setPicture(EMPTY);
            board[midRow][midCol].makeEmptyCell();
        }
    }

    public Checker getChecker(int row, int col) {
        return board[row][col];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("  0  1  2  3  4  5  6  7\n"); // выводим номера колонок

        for (int row = 0; row < board.length; row++) {
            result.append(row).append(" "); // выводим номер строки
            for (int col = 0; col < board.length; col++) {
                result.append(board[row][col].getPicture()); // клетка с шашкой
                result.append(" ");
            }

            result.append(row).append("\n"); // выводим номер строки еще раз
        }
        result.append("  0 1 2 3 4 5 6 7");
        return result.toString();
    }
}
