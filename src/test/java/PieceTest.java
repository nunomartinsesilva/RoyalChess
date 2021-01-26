import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board.Builder;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.move.MoveStatus;
import com.royalchess.engine.move.MoveTransition;
import com.royalchess.engine.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    public void testMiddleQueenOnEmptyBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK,  false, false));
        // White Layout
        builder.setPiece(new Queen(36,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 31);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testLegalMoveAllAvailable() {

        final Board.Builder boardBuilder = new Board.Builder();
        // Black Layout
        boardBuilder.setPiece(new King(4,Alliance.BLACK, false, false));
        boardBuilder.setPiece(new Knight(28,Alliance.BLACK));
        // White Layout
        boardBuilder.setPiece(new Knight(36,Alliance.WHITE));
        boardBuilder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        boardBuilder.setNextMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 13);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d6"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f6"));
        final Move wm3 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c5"));
        final Move wm4 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g5"));
        final Move wm5 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c3"));
        final Move wm6 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g3"));
        final Move wm7 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d2"));
        final Move wm8 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        assertTrue(whiteLegals.contains(wm3));
        assertTrue(whiteLegals.contains(wm4));
        assertTrue(whiteLegals.contains(wm5));
        assertTrue(whiteLegals.contains(wm6));
        assertTrue(whiteLegals.contains(wm7));
        assertTrue(whiteLegals.contains(wm8));

        final Board.Builder boardBuilder2 = new Board.Builder();
        // Black Layout
        boardBuilder2.setPiece(new King(4,Alliance.BLACK, false, false));
        boardBuilder2.setPiece(new Knight(28,Alliance.BLACK));
        // White Layout
        boardBuilder2.setPiece(new Knight(36,Alliance.WHITE));
        boardBuilder2.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        boardBuilder2.setNextMoveMaker(Alliance.BLACK);
        final Board board2 = boardBuilder2.build();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();

        final Move bm1 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d7"));
        final Move bm2 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f7"));
        final Move bm3 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c6"));
        final Move bm4 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g6"));
        final Move bm5 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("c4"));
        final Move bm6 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("g4"));
        final Move bm7 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d3"));
        final Move bm8 = Move.MoveFactory
                .createMove(board2, BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("f3"));

        assertEquals(blackLegals.size(), 13);

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));
        assertTrue(blackLegals.contains(bm3));
        assertTrue(blackLegals.contains(bm4));
        assertTrue(blackLegals.contains(bm5));
        assertTrue(blackLegals.contains(bm6));
        assertTrue(blackLegals.contains(bm7));
        assertTrue(blackLegals.contains(bm8));
    }

    @Test
    public void testKnightInCorners() {
        final Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(4,Alliance.BLACK, false, false));
        boardBuilder.setPiece(new Knight(0,Alliance.BLACK));
        boardBuilder.setPiece(new Knight(56,Alliance.WHITE));
        boardBuilder.setPiece(new King(60,Alliance.WHITE, false, false));
        boardBuilder.setNextMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 7);
        assertEquals(blackLegals.size(), 7);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("b3"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("c2"));
        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        final Move bm1 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("b6"));
        final Move bm2 = Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("c7"));
        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }

    @Test
    public void testMiddleBishopOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(35,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("a7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("b6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("c5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("f2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("g1"))));
    }

    @Test
    public void testTopLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(0,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(board.getTile(0).getPiece(), board.getTile(0).getPiece());
        assertNotNull(board.getTile(0).getPiece());
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a8"), BoardUtils.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testTopRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(7,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h8"), BoardUtils.getCoordinateAtPosition("a1"))));
    }

    @Test
    public void testBottomLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(56,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a1"), BoardUtils.getCoordinateAtPosition("h8"))));
    }

    @Test
    public void testBottomRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Bishop(63,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h1"), BoardUtils.getCoordinateAtPosition("a8"))));
    }

    @Test
    public void testMiddleRookOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Rook(36,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.getWhitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.getBlackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testPawnPromotion() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Rook(3,Alliance.BLACK));
        builder.setPiece(new King(22,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(15,Alliance.WHITE));
        builder.setPiece(new King(52,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "h7"), BoardUtils.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.getCurrentPlayer().makeMove(m1);
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.getTransitionBoard().getCurrentPlayer().makeMove(m2);
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.getCurrentPlayer().makeMove(m3);
        assertEquals(t3.getMoveStatus(), MoveStatus.DONE);
    }

    @Test
    public void testSimpleWhiteEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.getCurrentPlayer().makeMove(m1);
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e8"), BoardUtils.getCoordinateAtPosition("d8"));
        final MoveTransition t2 = t1.getTransitionBoard().getCurrentPlayer().makeMove(m2);
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e5"));
        final MoveTransition t3 = t2.getTransitionBoard().getCurrentPlayer().makeMove(m3);
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5"));
        final MoveTransition t4 = t3.getTransitionBoard().getCurrentPlayer().makeMove(m4);
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e5"), BoardUtils.getCoordinateAtPosition("d6"));
        final MoveTransition t5 = t4.getTransitionBoard().getCurrentPlayer().makeMove(m5);
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testSimpleBlackEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e1"), BoardUtils.getCoordinateAtPosition("d1"));
        final MoveTransition t1 = board.getCurrentPlayer().makeMove(m1);
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils.getCoordinateAtPosition("d5"));
        final MoveTransition t2 = t1.getTransitionBoard().getCurrentPlayer().makeMove(m2);
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"), BoardUtils.getCoordinateAtPosition("c1"));
        final MoveTransition t3 = t2.getTransitionBoard().getCurrentPlayer().makeMove(m3);
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("d4"));
        final MoveTransition t4 = t3.getTransitionBoard().getCurrentPlayer().makeMove(m4);
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t5 = t4.getTransitionBoard().getCurrentPlayer().makeMove(m5);
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
        final Move m6 = Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"), BoardUtils.getCoordinateAtPosition("e3"));
        final MoveTransition t6 = t5.getTransitionBoard().getCurrentPlayer().makeMove(m6);
        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testEnPassant2() {
        final Board board = Board.createStandardBoard();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e2"), BoardUtils.getCoordinateAtPosition("e3"));
        final MoveTransition t1 = board.getCurrentPlayer().makeMove(m1);
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h7"), BoardUtils.getCoordinateAtPosition("h5"));
        final MoveTransition t2 = t1.getTransitionBoard().getCurrentPlayer().makeMove(m2);
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e3"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t3 = t2.getTransitionBoard().getCurrentPlayer().makeMove(m3);
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h5"), BoardUtils.getCoordinateAtPosition("h4"));
        final MoveTransition t4 = t3.getTransitionBoard().getCurrentPlayer().makeMove(m4);
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"), BoardUtils.getCoordinateAtPosition("g4"));
        final MoveTransition t5 = t4.getTransitionBoard().getCurrentPlayer().makeMove(m5);
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testWhiteKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);
        final Move wm1 = Move.MoveFactory
                .createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("g1"));
        assertTrue(t6.getTransitionBoard().getWhitePlayer().getLegalMoves().contains(wm1));
        final MoveTransition t7 = t6.getTransitionBoard().getCurrentPlayer().makeMove(wm1);
        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t7.getTransitionBoard().getWhitePlayer().isCastled());
        assertFalse(t7.getTransitionBoard().getWhitePlayer().isKingSideCastleCapable());
        assertFalse(t7.getTransitionBoard().getWhitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testWhiteQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h7"),
                        BoardUtils.getCoordinateAtPosition("h6")));
        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h6"),
                        BoardUtils.getCoordinateAtPosition("h5")));
        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);
        final Move wm1 = Move.MoveFactory
                .createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition(
                        "e1"), BoardUtils.getCoordinateAtPosition("c1"));
        assertTrue(t10.getTransitionBoard().getCurrentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t11 = t10.getTransitionBoard().getCurrentPlayer().makeMove(wm1);
        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t11.getTransitionBoard().getWhitePlayer().isCastled());
        assertFalse(t11.getTransitionBoard().getWhitePlayer().isKingSideCastleCapable());
        assertFalse(t11.getTransitionBoard().getWhitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d3"),
                        BoardUtils.getCoordinateAtPosition("d4")));
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);
        final Move wm1 = Move.MoveFactory
                .createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("g8"));
        assertTrue(t7.getTransitionBoard().getCurrentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t8 = t7.getTransitionBoard().getCurrentPlayer().makeMove(wm1);
        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t8.getTransitionBoard().getBlackPlayer().isCastled());
        assertFalse(t8.getTransitionBoard().getBlackPlayer().isKingSideCastleCapable());
        assertFalse(t8.getTransitionBoard().getBlackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d3")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("e7")));
        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("d2")));
        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));
        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("d7")));
        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                                BoardUtils.getCoordinateAtPosition("f3")));
        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);
        final Move wm1 = Move.MoveFactory
                .createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition(
                        "e8"), BoardUtils.getCoordinateAtPosition("c8"));
        assertTrue(t11.getTransitionBoard().getCurrentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t12 = t11.getTransitionBoard().getCurrentPlayer().makeMove(wm1);
        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t12.getTransitionBoard().getBlackPlayer().isCastled());
        assertFalse(t12.getTransitionBoard().getBlackPlayer().isKingSideCastleCapable());
        assertFalse(t12.getTransitionBoard().getBlackPlayer().isQueenSideCastleCapable());
    }




    @Test
    public void testKingEquality() {
        final Board board = Board.createStandardBoard();
        final Board board2 = Board.createStandardBoard();
        assertEquals(board.getTile(60).getPiece(), board2.getTile(60).getPiece());
        assertNotNull(board.getTile(60).getPiece());
    }


    @Test
    public void testHashCode() {
        final Board board = Board.createStandardBoard();
        Set<Piece> blackPiecesSet = new HashSet<Piece>(board.getBlackPieces());
        Set<Piece> whitePiecesSet = new HashSet<Piece>(board.getWhitePieces());
        assertEquals(whitePiecesSet.size(), 16);
        assertEquals(blackPiecesSet.size(), 16);
    }
}