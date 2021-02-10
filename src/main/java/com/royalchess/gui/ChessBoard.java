package com.royalchess.gui;

import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.Tile;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.move.MoveStatus;
import com.royalchess.engine.move.MoveTransition;
import com.royalchess.engine.pieces.Piece;
import com.royalchess.engine.player.Alliance;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class ChessBoard extends JFrame implements MouseListener, MouseMotionListener
{
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;

    private Board gameBoard;

    private int xAdjustment;
    private int yAdjustment;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece movedPiece;
    private final MoveLog moveLog;
    private final MoveHistory moveHistory;
    private boolean popUp;


    public ChessBoard()
    {
        Dimension boardSize = new Dimension(600, 600);


        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize( boardSize );
        layeredPane.addMouseListener( this );
        layeredPane.addMouseMotionListener( this );
        getContentPane().add(layeredPane);


        chessBoard = new JPanel();
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        this.gameBoard = Board.createStandardBoard();
        this.moveLog = new MoveLog();
        this.moveHistory = new MoveHistory();
        this.moveHistory.setBorder(BorderFactory.createEmptyBorder(7,0,0,10));
        add(this.moveHistory, BorderLayout.EAST);
        this.popUp=false;
        chessBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chessBoard.setBackground(Color.decode("#1b1a1c"));


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
            {
                JPanel square = new JPanel( new BorderLayout() );
                square.setBackground( (i + j) % 2 == 0 ? Color.decode("#eeeeee") : Color.darkGray );
                chessBoard.add( square );
            }
        }

        assignTilePieceIcon(gameBoard);
    }

    private void assignTilePieceIcon(final Board board) {
        for(int i = 0; i < 64; i++) {
            if(board.getTile(i).isTileOccupied()) {
                ImageIcon pieceIcon = new ImageIcon("../art/" + board.getTile(i).getPiece().getPieceAlliance().toString().substring(0,1)
                        + board.getTile(i).getPiece().toString() + ".png"); // add an image here
                JLabel piece = new JLabel( pieceIcon );
                JPanel panel = (JPanel)chessBoard.getComponent( i );
                panel.add(piece);
                panel.validate();

            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        //first click
        /*if (sourceTile == null) {
            Component c =  chessBoard.findComponentAt(e.getX(),e.getY());
            if (c instanceof JPanel) return;
            chessPiece = (JLabel) c;
            c = c.getParent();
            for (int i =0; i< 64; i++) {
                if (chessBoard.getComponent(i) == c) {
                    sourceTile = gameBoard.getTile(i);
                    movedPiece = gameBoard.getTile(i).getPiece();
                    if (movedPiece == null) {
                        sourceTile = null;
                    }
                }
            }
            highlightLegals(gameBoard);

        }
        else {
            //second click
            Component c =  chessBoard.findComponentAt(e.getX(),e.getY());
            for (int i=0; i <64; i++) {
                if(chessBoard.getComponent(i) == c.getParent()) {
                    destinationTile = gameBoard.getTile(i);
                    final Move move = Move.MoveFactory.createMove(gameBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                    final MoveTransition transition = gameBoard.getCurrentPlayer().makeMove(move);
                    if (transition.getMoveStatus() == MoveStatus.DONE) {
                        JPanel panel = (JPanel)chessBoard.getComponent(sourceTile.getTileCoordinate());
                        panel.setBorder(BorderFactory.createLineBorder(Color.gray,1));
                        panel.setBorder(null);
                        panel.removeAll();
                        if (c instanceof JLabel)
                        {
                            Container parent = c.getParent();
                            parent.remove(0);
                            parent.add( chessPiece );
                            parent.validate();
                        }
                        else
                        {
                            ((Container) c).add(chessPiece);
                            c.validate();
                        }
                        moveLog.addMove(move);
                        moveHistory.printMove(moveLog,gameBoard);
                        gameBoard = transition.getTransitionBoard();
                        SFXUtils.playSound("move");
                        System.out.println(gameBoard);
                        System.out.println("█████ move done:" + move.toString() + " █████");
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;
                        clearLegals();
                        isWin(gameBoard);

                    }
                }
            }
        }*/



    }

    public void mousePressed(MouseEvent e) {
        if (popUp == false) {
            Component c =  chessBoard.findComponentAt(e.getX(), e.getY());

            if (c instanceof JPanel) return;

            Point parentLocation = c.getParent().getLocation();
            xAdjustment = parentLocation.x - e.getX();
            yAdjustment = parentLocation.y - e.getY();
            chessPiece = (JLabel)c;
            chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            c = c.getParent();
            for (int i =0; i< 64; i++) {
                if (chessBoard.getComponent(i) == c) {
                    sourceTile = gameBoard.getTile(i);
                    movedPiece = gameBoard.getTile(i).getPiece();
                    highlightLegals(gameBoard);
                    if (movedPiece == null) {
                        sourceTile = null;
                    }
                }
            }

            layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
            layeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    public void mouseDragged(MouseEvent me) {
        if (popUp == false) {
            if (chessPiece == null) return;

            //  The drag location should be within the bounds of the chess board

            int x = me.getX() + xAdjustment;
            int xMax = layeredPane.getWidth() - chessPiece.getWidth();
            x = Math.min(x, xMax);
            x = Math.max(x, 0);

            int y = me.getY() + yAdjustment;
            int yMax = layeredPane.getHeight() - chessPiece.getHeight();
            y = Math.min(y, yMax);
            y = Math.max(y, 0);

            chessPiece.setLocation(x, y);
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (popUp == false) {
            layeredPane.setCursor(null);

            if (chessPiece == null) {return;}

            chessPiece.setVisible(false);
            layeredPane.remove(chessPiece);
            chessPiece.setVisible(true);

            int xMax = layeredPane.getWidth() - chessPiece.getWidth();
            int x = Math.min(e.getX(), xMax);
            x = Math.max(x, 0);

            int yMax = layeredPane.getHeight() - chessPiece.getHeight();
            int y = Math.min(e.getY(), yMax);
            y = Math.max(y, 0);

            Component c =  chessBoard.findComponentAt(x, y);
            for (int i =0; i< 64; i++) {
                if (chessBoard.getComponent(i) == c || chessBoard.getComponent(i) == c.getParent()) {
                    destinationTile = gameBoard.getTile(i);

                }
            }
            final Move move = Move.MoveFactory.createMove(gameBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
            final MoveTransition transition = gameBoard.getCurrentPlayer().makeMove(move);
            if (transition.getMoveStatus() == MoveStatus.DONE) {
                gameBoard = transition.getTransitionBoard();
                SFXUtils.playSound("move");
                System.out.println(gameBoard);
                System.out.println("█████ move done:" + move.toString() + " █████");
                SFXUtils.playSound("move");
                moveLog.addMove(move);
                moveHistory.printMove(moveLog,gameBoard);
                sourceTile = null;
                destinationTile = null;
                movedPiece = null;

                if (c instanceof JLabel)
                {
                    Container parent = c.getParent();
                    parent.removeAll();
                    parent.add( chessPiece );
                    chessPiece = null;
                    parent.validate();
                }
                else
                {
                    Container container = (Container)c;
                    container.removeAll();
                    container.add( chessPiece );
                    chessPiece = null;
                    container.validate();
                }
                assignTilePieceIcon(gameBoard);
                isWin(gameBoard);

            }
            else {
                JPanel panel = (JPanel)chessBoard.getComponent(sourceTile.getTileCoordinate() );
                panel.add(chessPiece);
            }
            clearLegals();

        }
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void isWin(final Board board)  {
        if (board.getCurrentPlayer().isInCheckMate() || board.getCurrentPlayer().getOpponent().isInCheckMate()) {
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

    public void displayWinPopUp(final String message)  {
        popUp=true;
        Object[] options = {"Rematch", "Exit"};
        int result = JOptionPane.showOptionDialog(layeredPane, message, "Checkmate!", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        if (result == JOptionPane.YES_OPTION) {
            gameBoard = Board.createStandardBoard();
            for (int i =0; i<64; i++) {
                JPanel panel = (JPanel)chessBoard.getComponent(i);
                panel.setBorder(BorderFactory.createLineBorder(Color.gray));
                panel.setBorder(null);
                panel.removeAll();
                panel.validate();
            }
            assignTilePieceIcon(gameBoard);
            moveLog.clear();
            moveHistory.clearHistory();
            popUp = false;
        } else if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }



    private void highlightLegals (final Board board) {
            for(final Move move: pieceLegalMoves(board)) {
                MoveTransition transition = board.getCurrentPlayer().makeMove(move);
                if(transition.getMoveStatus() != MoveStatus.DONE){
                    continue;
                }

                if(move.getAttackedPiece() == null) {

                    ImageIcon dotIcon = new ImageIcon("../art/misc/golden_dot.png"); // add an image here
                    JLabel dot = new JLabel( dotIcon );
                    JPanel panel = (JPanel)chessBoard.getComponent( move.getDestinationCoordinate() );
                    panel.add(dot);
                    panel.setBorder(BorderFactory.createLineBorder(Color.darkGray,1));
                    panel.validate();
                }
                else if(move.getAttackedPiece() != null) {
                    JPanel panel = (JPanel)chessBoard.getComponent( move.getDestinationCoordinate() );
                    panel.setBorder(BorderFactory.createLineBorder(Color.red,2));
                    panel.validate();
                }
            }

    }


    private void clearLegals() {
        for(int i =0; i<64; i++) {
            JPanel panel = (JPanel)chessBoard.getComponent(i);
            panel.setBorder(null);
            if (!gameBoard.getTile(i).isTileOccupied()) {
                panel.removeAll();
                panel.validate();
            }
            if (panel.getComponentCount() == 2) {
                panel.remove(0);
                panel.validate();
            }
        }
    }


    private Collection<Move> pieceLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        if(movedPiece != null && movedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
            if(movedPiece.getPieceType() == Piece.PieceType.KING && movedPiece.isFirstMove()){
                legalMoves.addAll(board.getCurrentPlayer().calculateKingCastles(board.getCurrentPlayer().getLegalMoves(),
                        board.getCurrentPlayer().getOpponent().getLegalMoves()));

            }
            legalMoves.addAll(movedPiece.calculateLegalMoves(board));
            return legalMoves;
        }
        return Collections.emptyList();
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


}