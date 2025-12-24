package br.com.tenryuusoft.core;

public class Position {
    //inciando as variaveis de coluna e linha como private
    private int row; private int column; 

    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
}
