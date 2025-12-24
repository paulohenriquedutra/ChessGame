package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

public class Bishop extends Piece {
  public Bishop(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean pieceRule(Position newPosition, Piece[][] board) {
      int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
      int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());

      //Movimento (verifica se o movimento é diagonal)
      if (rowDiff != colDiff) { 
          return false; 
      }

      int rowStep = newPosition.getRow() > position.getRow() ? 1 : -1;
      int colStep = newPosition.getColumn() > position.getColumn() ? 1 : -1;

      int steps = rowDiff - 1;

      // verifica se tem algo se o caminho está livre
      for (int i = 1; i <= steps; i++) {
          if (board[position.getRow() + i * rowStep][position.getColumn() + i * colStep] != null) {
              return false; 
          }
      }

      // Captura (Verifica se o espaço de destino está vazio para liberar o movimento ou captura)
      Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
      if (destinationPiece == null) {
          return true; 
      } else if (destinationPiece.getColor() != this.getColor()) {
          return true; 
      }

      return false;
  }
}