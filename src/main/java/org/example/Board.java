package org.example;

public class Board {

    public static final String BLACK ="⚫️";
    public static final String WHITE ="⚪️";
    public static final String WHITE_SQUARE ="⬜️";
    public static final String IS_EMPTY ="⬛️";
    public static final String DAMKA_BLACK ="  ";
    public static final String DAMKA_WHITE ="  ";
        public static void main(String[] args) {
            int size = 8;
            String[][] board = new String[size][size];

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if ((row + col) % 2 != 0) {
                        if (row < 3) {
                            board[row][col] = BLACK;
                        } else if (row > 4) {
                            board[row][col] = WHITE;
                        } else {
                            board[row][col] = IS_EMPTY;
                        }
                    } else {
                        board[row][col] = WHITE_SQUARE;
                    }
                }
            }


// Отображение доски с фигурами
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    System.out.print(board[row][col]);
                }
                System.out.println();
            }


        }
    }

