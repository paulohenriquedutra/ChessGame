package br.com.tenryuusoft.core;

import br.com.tenryuusoft.pieces.Bishop;
import br.com.tenryuusoft.pieces.Horse;
import br.com.tenryuusoft.pieces.King;
import br.com.tenryuusoft.pieces.Pawn;
import br.com.tenryuusoft.pieces.Piece;
import br.com.tenryuusoft.pieces.PieceColor;
import br.com.tenryuusoft.pieces.Queen;
import br.com.tenryuusoft.pieces.Rook;

public class Board {
    private Piece[][] board;
    public Board(){
        this.board = new Piece[8][8];
        setupPieces();
    }
    public void movePiece(Position start, Position end){
        //movendo na matriz, atualizando posição e limpando a posição antiga
        if (board[start.getRow()][start.getColumn()] != null && board[start.getRow()][start.getColumn()].pieceRule(end, board)){
            board[end.getRow()][end.getColumn()] = board[start.getRow()][start.getColumn()];
            board[end.getRow()][end.getColumn()].setPosition(end);
            board[start.getRow()][start.getColumn()] = null;
        }
    }
    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public void setPiece(int row, int column, Piece piece) {
    board[row][column] = piece;
    if (piece != null) {
        piece.setPosition(new Position(row, column));
    }
    }
    //Colocando as peças pretas e brancas nas posições
    private void setupPieces(){
        //Torres
        board[0][0] = new Rook(PieceColor.BLACK, new Position(0, 0));
        board[0][7] = new Rook(PieceColor.BLACK, new Position(0, 7));
        board[7][0] = new Rook(PieceColor.WHITE, new Position(7, 0));
        board[7][7] = new Rook(PieceColor.WHITE, new Position(7, 7));
        //Cavalos
        board[0][1] = new Horse(PieceColor.BLACK, new Position(0, 1));
        board[0][6] = new Horse(PieceColor.BLACK, new Position(0, 6));
        board[7][1] = new Horse(PieceColor.WHITE, new Position(7, 1));
        board[7][6] = new Horse(PieceColor.WHITE, new Position(7, 6));
        //Bispos
        board[0][2] = new Bishop(PieceColor.BLACK, new Position(0, 2));
        board[0][5] = new Bishop(PieceColor.BLACK, new Position(0, 5));
        board[7][2] = new Bishop(PieceColor.WHITE, new Position(7, 2));
        board[7][5] = new Bishop(PieceColor.WHITE, new Position(7, 5));
        //Rainhas
        board[0][3] = new Queen(PieceColor.BLACK, new Position(0, 3));
        board[7][3] = new Queen(PieceColor.WHITE, new Position(7, 3));
        //Reis
        board[0][4] = new King(PieceColor.BLACK, new Position(0, 4));
        board[7][4] = new King(PieceColor.WHITE, new Position(7, 4));
        //Peão 
        for(int i = 0; i < 8; i++){ //utilizando laço para melhor manutenção de código
            board[1][i] = new Pawn(PieceColor.BLACK, new Position(1, i));
            board[6][i] = new Pawn(PieceColor.WHITE, new Position(6, i));
        }
    }
}
