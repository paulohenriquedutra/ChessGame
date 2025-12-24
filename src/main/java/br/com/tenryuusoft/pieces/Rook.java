package br.com.tenryuusoft.pieces;

import br.com.tenryuusoft.core.Position;;

public class Rook extends Piece {
  public Rook(PieceColor color, Position position) {
      super(color, position);
  }

  @Override
  public boolean pieceRule(Position newPosition, Piece[][] board) {
      // Movimento (Vertical e horizontal até o fim da coluna e linha, também impedindo o movimento se tiver uma peça na frente).
      if (position.getRow() == newPosition.getRow()) {
          int columnStart = Math.min(position.getColumn(), newPosition.getColumn()) + 1;
          int columnEnd = Math.max(position.getColumn(), newPosition.getColumn());
          for (int column = columnStart; column < columnEnd; column++) {
              if (board[position.getRow()][column] != null) { // 
                  return false;
              }
          }
      } else if (position.getColumn() == newPosition.getColumn()) {
          int rowStart = Math.min(position.getRow(), newPosition.getRow()) + 1;
          int rowEnd = Math.max(position.getRow(), newPosition.getRow());
          for (int row = rowStart; row < rowEnd; row++) {
              if (board[row][position.getColumn()] != null) {
                  return false; 
              }
          }
      } else {
          return false;
      }

      //Captura (Verifica se o espaço de destino está vazio para liberar o movimento)
      Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
      if (targetPiece == null) {
          return true;
      } else if (targetPiece.getColor() != this.getColor()) {
          return true; // se tiver uma peça adversária libera a captura
      }
      return false; 
  }
}