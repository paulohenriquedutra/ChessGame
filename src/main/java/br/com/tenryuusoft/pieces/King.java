package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

public class King extends Piece {
  public King(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean pieceRule(Position newPosition, Piece[][] board) {
      int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
      int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());

      // Movimento (1 espaço)
      boolean oneSquare = rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);

      if (!oneSquare) {
          return false; 
      }

      //Captura (Verifica se o espaço de destino está vazio para liberar o movimento ou captura)
      Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
      return destinationPiece == null || destinationPiece.getColor() != this.getColor();
  }
}