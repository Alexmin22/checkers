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

    //Проверка возможности хода
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol, boolean capture) {
        Checker checker = board[fromRow][fromCol];

        System.out.println(checker.toString() + " from " + board[toRow][toCol].toString() + " to ");
        if (board[toRow][toCol].getPicture().equals(EMPTY)) {
            System.out.println("зашел в иф");
            if (capture) {
                System.out.println("капча");
                int rowDiff = toRow - fromRow;
                int colDiff = toCol - fromCol;
                int midRow = fromRow + rowDiff / 2;
                int midCol = fromCol + colDiff / 2;
                Checker midChecker = board[midRow][midCol];
                if (midChecker.getPicture().equals(EMPTY) || midChecker.isBlack() == checker.isBlack()) {
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
        System.out.println("конец");
        return false;
    }

    //Перемещение шашки на указанные координаты
    public void moveChecker(int fromRow, int fromCol, int toRow, int toCol) {
        System.out.println("Зфшел в мувчекер");
        Checker checker = board[fromRow][fromCol];
        checker.move(toRow, toCol);
        board[toRow][toCol] = checker;
        board[fromCol][fromRow].setPicture(EMPTY);
        if (toRow == 0 && !checker.isKing() && !checker.isBlack()) {
            checker.setPicture(KING_WHITE);
            checker.makeKing();
        } else if (toRow == board.length - 1 && !checker.isKing() && checker.isBlack()) {
            checker.setPicture(KING_BLACK);
            checker.makeKing();
        }
    }

    //Проверка наличия обязательных ходов
    public boolean hasCaptureMoves(boolean black) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Checker checker = board[row][col];
                    if (canMove(row, col, row + 2, col + 2, true)
                            || canMove(row, col, row + 2, col - 2, true)
                            || canMove(row, col, row - 2, col + 2, true)
                            || canMove(row, col, row - 2, col - 2, true)) {
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
