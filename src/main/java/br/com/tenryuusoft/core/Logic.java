package br.com.tenryuusoft.core;

import br.com.tenryuusoft.pieces.King;
import br.com.tenryuusoft.pieces.Piece;
import br.com.tenryuusoft.pieces.PieceColor;

public class Logic {
    private Board board;
    private boolean whiteTurn = true;
    private boolean gameOver = false;
    private PieceColor winner = null;

    public Logic() {
        this.board = new Board();
    }

    //Movimento do jogo
    public boolean makeMove(Position start, Position end) {
        //verifica se o movimento é valido
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
        if (movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
            return false; 
        }

        if (movingPiece.pieceRule(end, board.getBoard())) {
            // Executa o movimento
            board.movePiece(start, end);
            whiteTurn = !whiteTurn; // troca os turnos
            return true;
        }
        return false;
    }
    //logica do "Check"
    public boolean Check(PieceColor kingColor) {
      Position kingPosition = findKing(kingColor);
      for (int row = 0; row < board.getBoard().length; row++) {
          for (int col = 0; col < board.getBoard()[row].length; col++) {
              Piece piece = board.getPiece(row, col);
              if (piece != null && piece.getColor() != kingColor) {
                  if (piece.pieceRule(kingPosition, board.getBoard())) { //verifica se o rei está em check
                      return true; 
                  }
              }
          }
      }
      return false;
    }
    //Encontrar a posição
    private Position findKing(PieceColor color) {
      for (int row = 0; row < board.getBoard().length; row++) {
          for (int col = 0; col < board.getBoard()[row].length; col++) {
              Piece piece = board.getPiece(row, col);
              if (piece instanceof King && piece.getColor() == color) {
                  return new Position(row, col);
              }
          }
      }
      return null;
    }
    //logica do "CheckMate"
    public boolean Checkmate(PieceColor kingColor) {
      if (!Check(kingColor)) { //verifica se rei está em check
          return false; 
      }

      Position kingPosition = findKing(kingColor);
      King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getColumn());

      // verificando se pode escapar do check
      for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
          for (int colOffset = -1; colOffset <= 1; colOffset++) {
              if (rowOffset == 0 && colOffset == 0) {
                  continue;
              }
              Position newPosition = new Position(kingPosition.getRow() + rowOffset, kingPosition.getColumn() + colOffset);
              if (positionOnBoard(newPosition) && king.pieceRule(newPosition, board.getBoard())) {
                  return false;
              }
          }
      }
      return true;
    }
    private boolean positionOnBoard(Position position) {
      return position.getRow() >= 0 && position.getRow() < board.getBoard().length && position.getColumn() >= 0 && position.getColumn() < board.getBoard()[0].length;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public PieceColor getWinner() {
        return winner;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
    public void resetGame() {
    this.board = new Board();
    this.whiteTurn = true;
    this.gameOver = false;
    this.winner = null;
}
}