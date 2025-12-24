package br.com.tenryuusoft;

import br.com.tenryuusoft.pieces.Piece;
import br.com.tenryuusoft.core.Board;
//Classe usada para testar trechos do codigo, ignore o conteudo

public class Teste {
    public static void main(String[] args) {
        Board board = new Board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece == null)
                    System.out.print(". ");
                else
                    System.out.print(piece.getClass().getSimpleName().charAt(0) + " ");
            }
            System.out.println();
        }
    }
}
