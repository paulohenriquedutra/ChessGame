package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;

public class Queen extends Piece {
  public Queen(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean pieceRule(Position newPosition, Piece[][] board) {
      if (newPosition.equals(this.position)) { //verifica se o movimento é para a mesma posição 
          return false;
      }

      int rowDiff = Math.abs(newPosition.getRow() - this.position.getRow());
      int colDiff = Math.abs(newPosition.getColumn() - this.position.getColumn());

      // Movimento (verifica se o movimento é reto ou diagonal)
      boolean straightLine = this.position.getRow() == newPosition.getRow()
              || this.position.getColumn() == newPosition.getColumn();

      boolean diagonal = rowDiff == colDiff;

      if (!straightLine && !diagonal) {
          return false; 
      }

      // Calculo da direção do movimento
      int rowDirection = Integer.compare(newPosition.getRow(), this.position.getRow());
      int colDirection = Integer.compare(newPosition.getColumn(), this.position.getColumn());

      // verifica peças no caminho
      int currentRow = this.position.getRow() + rowDirection;
      int currentCol = this.position.getColumn() + colDirection;
      while (currentRow != newPosition.getRow() || currentCol != newPosition.getColumn()) {
          if (board[currentRow][currentCol] != null) {
              return false; 
          }
          currentRow += rowDirection;
          currentCol += colDirection;
      }

      // Captura (Verifica se o espaço de destino está vazio para liberar o movimento ou captura)
      Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
      return destinationPiece == null || destinationPiece.getColor() != this.getColor();
  }
}