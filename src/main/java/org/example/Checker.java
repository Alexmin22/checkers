package org.example;

public class Checker {
    private int row;
    private int col;
    private boolean king;
    private boolean black;
    private boolean emptyCell;
    private String picture;

    public Checker(int row, int col, boolean black, String picture) {
        this.row = row;
        this.col = col;
        this.black = black;
        this.picture = picture;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void makeKing() {
        this.king = true;
    }

    public void makeEmptyCell() {this.emptyCell = true;}

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isKing() {
        return king;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isEmpty() {return emptyCell;}

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Checker{" +
                "row=" + row +
                ", col=" + col +
                ", king=" + king +
                ", black=" + black +
                ", picture='" + picture + '\'' +
                '}';
    }
}
