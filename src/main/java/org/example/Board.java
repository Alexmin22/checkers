package org.example;

public class Board {

    public static final String BLACK ="⚫️";
    public static final String WHITE ="⚪️";
    public static final String WHITE_SQUARE ="⬜️";
    public static final String EMPTY ="⬛️";
    public static final String KING_BLACK ="  ";
    public static final String KING_WHITE ="  ";
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
                    } else if (row == 3 || row == 4) {
                        board[row][col] = new Checker(row, col, false,
                                (row + col) % 2 == 0 ? EMPTY : WHITE_SQUARE);
                    }
                }
            }
        }

        public boolean canHit(boolean blackTurn) {
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    Checker checker = board[row][col];


                    if (checker.isBlack() == blackTurn) {
                        for (int a = -1; a < 2; a++) {
                            if (((checker.getRow() + a) > 0 && (checker.getRow() + a) < 7)
                                    && (checker.getCol() + a) > 0 && (checker.getCol() + a) < 7) {
                                Checker underFire = board[(checker.getRow() + a)][checker.getCol() + a];

                                if (!underFire.isBlack() == checker.isBlack()
                                        && !underFire.getPicture().equals(EMPTY)
                                        && !underFire.getPicture().equals(WHITE_SQUARE)) {

                                    System.out.println(underFire.getRow() + " " + underFire.getCol()+ "*/*/*/*/*/**/*/*/under*/*/**/*/");
                                    int rowDiff = underFire.getRow() - checker.getRow();
                                    int colDiff = underFire.getCol() - checker.getCol();

                                    if (underFire.getRow() + rowDiff >= 0 && underFire.getRow() + rowDiff < 8
                                            && underFire.getCol() + colDiff >= 0 && underFire.getCol() + colDiff < 8) {

                                        Checker possible = board[underFire.getRow() + rowDiff][underFire.getCol() + colDiff];

                                        if (possible.getPicture().equals(EMPTY)) {
                                            System.out.println(possible.getRow() + " <- row " + possible.getCol());
                                            System.out.println("Вам не обходимо бить!");
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
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol, boolean capture) {
        Checker checker = board[fromRow][fromCol];

        System.out.println(checker.toString() + " from " + board[toRow][toCol].toString() + " to ");
        if (board[toRow][toCol].getPicture().equals(EMPTY)) {

            if (capture) {
                System.out.println("капча");
                int rowDiff = toRow - fromRow;
                int colDiff = toCol - fromCol;
                int midRow = fromRow + rowDiff / 2;
                int midCol = fromCol + colDiff / 2;
                Checker midChecker = board[midRow][midCol];
                if (midChecker.getPicture().equals(WHITE_SQUARE) || midChecker.getPicture().equals(EMPTY) || midChecker.isBlack() == checker.isBlack()) {
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
    public void moveChecker(int fromRow, int fromCol, int toRow, int toCol) {

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
    }

    //Проверка наличия обязательных ходов
    public boolean hasCaptureMoves(boolean black) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                System.out.println("проверка "+col + " " + row);
                if ((row < 6 && col < 6) && canMove(row, col, row + 2, col + 2, true)) {
                    return true;
                } else if ((row < 6 && col >= 2) && canMove(row, col, row + 2, col - 2, true)) {
                    return true;
                } else if ((row >= 2 && col < 6) && canMove(row, col, row - 2, col + 2, true)) {
                    return true;
                } else if ((row >= 2 && col >= 2) && canMove(row, col, row - 2, col - 2, true)) {
                    return true;
                }
            }
        }
        return false;
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
