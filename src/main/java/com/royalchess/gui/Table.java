package com.royalchess.gui;

import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.board.Tile;
import com.royalchess.engine.pieces.Piece;
import com.royalchess.engine.move.MoveStatus;
import com.royalchess.engine.move.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    private boolean highlightLegalMoves;
    private int pieceLoadController;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(750,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);


    private final Color lightTileColor = Color.decode("#F7F7FF");
    private final Color darkTileColor =  Color.decode("#577399");


    public Table() throws IOException {
        this.gameFrame = new JFrame("Royal Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.pieceLoadController = 0;
        this.chessBoard = Board.createStandardBoard();

        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.moveLog = new MoveLog();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.boardPanel, BorderLayout.SOUTH);
        this.highlightLegalMoves = true;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar(  ) {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Open PGN File");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("HighLight Legal Moves", true);
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
            }
        });
        preferencesMenu.add(legalMoveHighlighterCheckbox);
        return preferencesMenu;
    }



    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;


        BoardPanel() throws IOException {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
            validate();
        }

        public void drawBoard(final Board board) throws IOException {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
            isWin(board);
        }

        public void isWin(final Board board) throws IOException {
            if (board.getCurrentPlayer().isInCheckMate()) {
                String message;
                if (board.getCurrentPlayer().getAlliance() == Alliance.BLACK) {
                    message = "White wins!";
                } else {
                    message = "Black wins!";
                }
                SFXUtils.playSound("win");
                displayWinPopUp(message);


            }
        }

        public void displayWinPopUp(final String message) throws IOException {
            Object[] options = {"Rematch", "Exit"};
            int result = JOptionPane.showOptionDialog(gameFrame, message, "Checkmate!", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
            if (result == JOptionPane.YES_OPTION) {
                chessBoard = Board.createStandardBoard();
                moveLog.clear();
                takenPiecesPanel.redo(moveLog);
                pieceLoadController = 0;
                boardPanel.drawBoard(chessBoard);
                gameHistoryPanel.redo(chessBoard, moveLog);
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }


    public static class MoveLog {
        private final List<Move> moves;
        MoveLog() {
            this.moves = new ArrayList<>();
        }
        public List<Move> getMoves() {
            return this.moves;
        }
        public Move getMove(final int index) {
            return this.moves.get(index);
        }
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        public int size() {
            return this.moves.size();
        }
        public void clear() {
            this.moves.clear();
        }
        public Move removeMove(int index) {
            return this.moves.remove(index);
        }
        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }

    private class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) throws IOException {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            highlightTileBorder(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        try {
                            boardPanel.drawBoard(chessBoard);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.println("piece deselected");
                    }
                    else if (isLeftMouseButton(e)) {
                        if(sourceTile == null) {
                            //first click
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                            System.out.println("piece selected");
                        }
                        else {
                            //second click
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
                            if (transition.getMoveStatus() == MoveStatus.DONE) {
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                                SFXUtils.playSound("move");
                                System.out.println(chessBoard);
                                System.out.println("█████ move done: " + move.toString() + " █████");

                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    gameHistoryPanel.redo(chessBoard, moveLog);
                                    takenPiecesPanel.redo(moveLog);
                                    boardPanel.drawBoard(chessBoard);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        public void drawTile(final Board board) throws IOException {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTileBorder(board);
            highlightLegals(board);
        }

        private void highlightTileBorder(final Board board) {
            if(humanMovedPiece != null &&
                    humanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance() &&
                    humanMovedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }

        private void assignTilePieceIcon(final Board board) throws IOException {
            if(pieceLoadController < 32 && moveLog.size() == 0 && board.getTile(this.tileId).isTileOccupied()) {
                this.removeAll();
                final BufferedImage image = ImageIO.read(new File("../art/" + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1)
                        + board.getTile(this.tileId).getPiece().toString() + ".png"));
                Image dimg = image.getScaledInstance(60 ,60, Image.SCALE_SMOOTH);
                add(new JLabel(new ImageIcon(dimg)));
                pieceLoadController++;
            }

            if(moveLog.getMoves().size() >  0 && board.getTile(this.tileId).getPiece() != null &&
                    board.getTile(this.tileId).getPiece().getPiecePosition() == moveLog.getMove(moveLog.size()-1).getDestinationCoordinate()) {
                this.removeAll();
                final BufferedImage image = ImageIO.read(new File("../art/" + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1)
                        + board.getTile(this.tileId).getPiece().toString() + ".png"));
                Image dimg = image.getScaledInstance(60 ,60, Image.SCALE_SMOOTH);
                add(new JLabel(new ImageIcon(dimg)));
            }
            if(!board.getTile(this.tileId).isTileOccupied()) {
                this.removeAll();
            }

        }

        private void assignTileColor() {
            boolean isLight = ((tileId + tileId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }

        private void highlightLegals (final Board board) throws IOException {
            if(highlightLegalMoves) {
                for(final Move move: pieceLegalMoves(board)) {
                    MoveTransition transition = board.getCurrentPlayer().makeMove(move);
                    if(transition.getMoveStatus() != MoveStatus.DONE){
                        continue;
                    }
                    if(move.getDestinationCoordinate() == this.tileId && move.getAttackedPiece() == null) {
                        final BufferedImage image = (ImageIO.read(new File("../art/misc/green_dot.png")));
                        Image dimg = image.getScaledInstance(10 ,10, Image.SCALE_SMOOTH);
                        add(new JLabel(new ImageIcon(dimg)));
                        System.out.println("green dotted");
                    }
                    else if(move.getDestinationCoordinate() == this.tileId && move.getAttackedPiece() != null) {
                        setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board){

            final List<Move> legalMoves = new ArrayList<>();

            if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
                if(humanMovedPiece.getPieceType() == Piece.PieceType.KING && humanMovedPiece.isFirstMove()){
                    legalMoves.addAll(board.getCurrentPlayer().calculateKingCastles(board.getCurrentPlayer().getLegalMoves(),
                            board.getCurrentPlayer().getOpponent().getLegalMoves()));

                }
                legalMoves.addAll(humanMovedPiece.calculateLegalMoves(board));
                return legalMoves;
            }
            return Collections.emptyList();
        }


    }
}
