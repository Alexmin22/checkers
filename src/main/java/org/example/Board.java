package org.example;

public class Board {

    public static final String BLACK ="⚫️";
    public static final String WHITE ="⚪️";
    public static final String WHITE_SQUARE ="⬜️";
    public static final String IS_EMPTY ="⬛️";
    public static final String KING_BLACK ="  ";
    public static final String KING_WHITE ="  ";
    private Checker[][] board;

    public Board() {
            //Инициализация доски
            board = new Checker[8][8];
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (row < 3) {
                        board[row][col] = new Checker(row, col, true,
                                (row + col) % 2 == 0 ? BLACK : WHITE_SQUARE);
                    } else if (row > 4) {
                        board[row][col] = new Checker(row, col, false,
                                (row + col) % 2 == 0 ? WHITE : WHITE_SQUARE);
                    } else {
                        board[row][col] = new Checker(row, col, false,
                                (row + col) % 2 == 0 ? IS_EMPTY : WHITE_SQUARE);
                    }
                }
            }
        }

    //Проверка возможности хода
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol, boolean capture) {
        Checker checker = board[fromRow][fromCol];
        if (board[toRow][toCol] != null || (capture && !checker.isKing())) {
            return false;
        }
        if (capture) {
            int rowDiff = toRow - fromRow;
            int colDiff = toCol - fromCol;
            int midRow = fromRow + rowDiff / 2;
            int midCol = fromCol + colDiff / 2;
            Checker midChecker = board[midRow][midCol];
            if (midChecker == null || midChecker.isBlack() == checker.isBlack()) {
                return false;
            }
        } else {
            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);
            if (checker.isKing() && rowDiff == colDiff) {
                return true;
            } else if (!checker.isKing() && ((checker.isBlack() && toRow > fromRow && rowDiff == 1 && colDiff == 1)
                    || (!checker.isBlack() && toRow < fromRow && rowDiff == 1 && colDiff == 1))) {
                return true;
            }
        }
        return false;
    }

    //Перемещение шашки на указанные координаты
    public void moveChecker(int fromRow, int fromCol, int toRow, int toCol) {
        Checker checker = board[fromRow][fromCol];
        checker.move(toRow, toCol);
        board[fromRow][fromCol] = null;
        board[toRow][toCol] = checker;
        if (toRow == 0 && !checker.isKing() && !checker.isBlack()) {
            checker.makeKing();
        } else if (toRow == board.length - 1 && !checker.isKing() && checker.isBlack()) {
            checker.makeKing();
        }
    }

    //Проверка наличия обязательных ходов
    public boolean hasCaptureMoves(boolean black) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Checker checker = board[row][col];
                if (checker != null && checker.isBlack() == black) {
                    if (canMove(row, col, row + 2, col + 2, true)
                            || canMove(row, col, row + 2, col - 2, true)
                            || canMove(row, col, row - 2, col + 2, true)
                            || canMove(row, col, row - 2, col - 2, true)) {
                        return true;
                    }
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
//        result.append("  0  1  2  3  4  5  6  7\n"); // выводим номера колонок
        for (int row = 0; row < board.length; row++) {
            result.append(row).append(" "); // выводим номер строки
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    result.append(IS_EMPTY); // пустая клетка
                } else {
                    result.append(board[row][col].getPicture()); // клетка с шашкой
                }
                result.append(" ");
            }
//            result.append(row).append("\n"); // выводим номер строки еще раз
        }
        result.append("  0 1 2 3 4 5 6 7");
        return result.toString();
    }
        }
