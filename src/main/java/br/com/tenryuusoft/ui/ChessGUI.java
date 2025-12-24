package br.com.tenryuusoft.ui;

import javax.swing.*;

import br.com.tenryuusoft.core.Board;
import br.com.tenryuusoft.core.Logic;
import br.com.tenryuusoft.core.Position;
import br.com.tenryuusoft.pieces.King;
import br.com.tenryuusoft.pieces.Piece;
import br.com.tenryuusoft.pieces.PieceColor;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ChessGUI extends JFrame {
    private Logic logic;
    private final JButton[][] buttons = new JButton[8][8];
    private Position selectedPosition = null;

    private JLabel turnLabel;
    private JButton resetButton;
    private JPanel boardPanel;

    public ChessGUI() {
        logic = new Logic();

        setTitle("Xadrez - Protótipo");
        setSize(640, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        turnLabel = new JLabel("Turno: Brancas");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));

        resetButton = new JButton("Resetar");
        resetButton.addActionListener(e -> {
            logic.resetGame();
            selectedPosition = null;
            clearHighlights();
            updateBoard();
            updateTurnLabel();
        });

        topPanel.add(turnLabel);
        topPanel.add(resetButton);

        add(topPanel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(8, 8));
        add(boardPanel, BorderLayout.CENTER);

        initBoard();
        updateBoard();

        setVisible(true);
    }

    private Board getBoardFromLogic() {
        try {
            Field field = Logic.class.getDeclaredField("board");
            field.setAccessible(true);
            return (Board) field.get(logic);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar o tabuleiro da lógica", e);
        }
    }

    private void initBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton btn = new JButton();
                btn.setFocusPainted(false);
                btn.setBackground((row + col) % 2 == 0
                        ? new Color(235, 235, 208)
                        : new Color(119, 148, 85));

                int r = row;
                int c = col;
                btn.addActionListener(e -> handleClick(r, c));

                buttons[row][col] = btn;
                boardPanel.add(btn);
            }
        }
    }

    private void handleClick(int row, int col) {
        Board board = getBoardFromLogic();
        Piece piece = board.getPiece(row, col);

        if (selectedPosition == null) {
            if (piece != null) {
                selectedPosition = new Position(row, col);
                highlightMoves(piece, board);
            }
        } else {
            Position target = new Position(row, col);

            if (logic.makeMove(selectedPosition, target)) {
                clearHighlights();
                selectedPosition = null;
                updateBoard();
                updateTurnLabel();

                checkGameOver(board);

                PieceColor turno = getTurnColor();
                if (logic.Checkmate(turno)) {
                    JOptionPane.showMessageDialog(this, turno + " está em xeque-mate!");
                }
            } else {
                clearHighlights();
                selectedPosition = null;
            }
        }
    }

    private void updateTurnLabel() {
        PieceColor turno = getTurnColor();
        turnLabel.setText("Turno: " + (turno == PieceColor.WHITE ? "Brancas" : "Pretas"));
    }

    private void highlightMoves(Piece piece, Board board) {
        clearHighlights();
        ArrayList<Position> validMoves = new ArrayList<>();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position pos = new Position(r, c);

                if (piece.pieceRule(pos, board.getBoard())) {
                    validMoves.add(pos);
                }
            }
        }

        for (Position pos : validMoves) {
            buttons[pos.getRow()][pos.getColumn()].setBackground(new Color(186, 202, 68));
        }
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                buttons[r][c].setBackground((r + c) % 2 == 0
                        ? new Color(235, 235, 208)
                        : new Color(119, 148, 85));
            }
        }
    }

    private void updateBoard() {
        Board board = getBoardFromLogic();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                JButton btn = buttons[row][col];

                if (piece != null) {
                    String color = piece.getColor() == PieceColor.WHITE ? "white" : "black";
                    String name = piece.getClass().getSimpleName().toLowerCase();
                    String path = "/pieces/" + color + "_" + name + ".png";

                    try {
                        ImageIcon icon = new ImageIcon(getClass().getResource(path));
                        Image scaled = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                        btn.setIcon(new ImageIcon(scaled));
                        btn.setText("");
                    } catch (Exception e) {
                        btn.setText(name.substring(0, 1).toUpperCase());
                        btn.setIcon(null);
                    }
                } else {
                    btn.setIcon(null);
                    btn.setText("");
                }
            }
        }
    }

    private void checkGameOver(Board board) {
        boolean whiteKingAlive = false;
        boolean blackKingAlive = false;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece instanceof King) {
                    if (piece.getColor() == PieceColor.WHITE) whiteKingAlive = true;
                    else blackKingAlive = true;
                }
            }
        }

        if (!whiteKingAlive || !blackKingAlive) {
            String winner = whiteKingAlive ? "Brancas" : "Negras";
            JOptionPane.showMessageDialog(this, "Game Over — " + winner + " venceram!");
            System.exit(0);
        }
    }

    private PieceColor getTurnColor() {
        try {
            Field field = Logic.class.getDeclaredField("whiteTurn");
            field.setAccessible(true);
            boolean whiteTurn = (boolean) field.get(logic);
            return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
        } catch (Exception e) {
            return PieceColor.WHITE;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
