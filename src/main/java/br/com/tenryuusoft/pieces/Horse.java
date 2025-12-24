package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

public class Horse extends Piece {
   public Horse(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean pieceRule(Position newPosition, Piece[][] board) {
      if (newPosition.equals(this.position)) {
          return false; 
      }

      int rowDiff = Math.abs(this.position.getRow() - newPosition.getRow());
      int colDiff = Math.abs(this.position.getColumn() - newPosition.getColumn());

      // Movimento (Verifica se está fazendo o "L"....) 
      boolean pieceRule = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);

      if (!pieceRule) {
          return false; 
      }
      // Captura (Verifica se o espaço de destino está vazio para liberar o movimento)
      Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
      if (targetPiece == null) {
          return true;
      } else { // se tiver uma peça adversária libera a captura
          return targetPiece.getColor() != this.getColor();
      }
  }
}