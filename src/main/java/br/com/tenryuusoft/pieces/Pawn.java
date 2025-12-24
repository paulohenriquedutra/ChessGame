package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

public class Pawn extends Piece{
    public Pawn(PieceColor color, Position position){
        super(color, position);
    }
    @Override //Notação pro compilador interpretar como uma substituição de um metodo existente

    public boolean pieceRule(Position newPosition, Piece[][] board){
        int forwardDirection = color == PieceColor.WHITE ? -1 : 1;
        int rowDiff = (newPosition.getRow() - position.getRow()) * forwardDirection;
        int columnDiff = newPosition.getColumn() - position.getColumn();

        // Movimento pra frente (1 espaço se tiver vazio)
        if (columnDiff == 0 && rowDiff == 1 && board[newPosition.getRow()][newPosition.getColumn()] == null) {
            return true;
        }

        // Primeiro movimento (2 espaços)
        boolean isStartingPosition = (color == PieceColor.WHITE && position.getRow() == 6) || (color == PieceColor.BLACK && position.getRow() == 1);
        if (columnDiff == 0 && rowDiff == 2 && isStartingPosition && board[newPosition.getRow()][newPosition.getColumn()] == null) {
            int middleRow = position.getRow() + forwardDirection;
            if (board[middleRow][position.getColumn()] == null) {
                return true;
            }
        }

        // Captura (1 espaço diagonal)
        if (Math.abs(columnDiff) == 1 && rowDiff == 1 && board[newPosition.getRow()][newPosition.getColumn()] != null && board[newPosition.getRow()][newPosition.getColumn()].color != this.color) {
            return true;
        }

        return false;
    }
}