package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

//classe que as peças vão herdar
public abstract class Piece{
    protected Position position;
    protected PieceColor color;

    public Piece(PieceColor color, Position position){
        this.color = color;
        this.position = position;
    }
    
    public PieceColor getColor(){
        return color;
    }
    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public abstract boolean pieceRule(Position newPosition, Piece[][] board);
}



