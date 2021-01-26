import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.move.MoveStatus;
import com.royalchess.engine.move.MoveTransition;
import com.royalchess.engine.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void testSimpleEvaluation() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer().makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        assertEquals(t1.getMoveStatus(), MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard().getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testIllegalMove() {
        final Board board = Board.createStandardBoard();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                BoardUtils.getCoordinateAtPosition("e6"));
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(m1);
        assertEquals(t1.getMoveStatus(), MoveStatus.ILLEGAL_MOVE);
    }

    @Test
    public void testDiscoveredCheck() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4, Alliance.BLACK, false, false));
        builder.setPiece(new Rook(24, Alliance.BLACK));
        // White Layout
        builder.setPiece(new Bishop(44, Alliance.WHITE));
        builder.setPiece(new Rook(52, Alliance.WHITE));
        builder.setPiece(new King(58, Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e3"),
                        BoardUtils.getCoordinateAtPosition("b6")));
        assertEquals(t1.getMoveStatus(), MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheck());
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("b5")));
        assertNotEquals(t2.getMoveStatus(),MoveStatus.DONE);
        assertEquals(t2.getMoveStatus(), MoveStatus.LEAVES_PLAYER_IN_CHECK);
        final MoveTransition t3 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testBug() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c2"),
                        BoardUtils.getCoordinateAtPosition("c3")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("a6")));
        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("a4")));
        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));
        assertNotEquals(t4.getMoveStatus(),MoveStatus.DONE);
    }

    @Test
    public void testAnandKramnikStaleMate() {

        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(21,Alliance.BLACK));
        builder.setPiece(new King(36,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(29,Alliance.WHITE));
        builder.setPiece(new King(31,Alliance.WHITE, false, false));
        builder.setPiece(new Pawn(39,Alliance.WHITE));
        // Set the current player
        builder.setNextMoveMaker(Alliance.BLACK);
        final Board board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(2,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(10,Alliance.WHITE));
        builder.setPiece(new King(26,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c5"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(0,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Pawn(16,Alliance.WHITE));
        builder.setPiece(new King(17,Alliance.WHITE, false, false));
        builder.setPiece(new Bishop(19,Alliance.WHITE));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        assertFalse(board.getCurrentPlayer().isInStaleMate());
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a7")));
        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testFoolsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        assertTrue(t4.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("a7"),
                        BoardUtils.getCoordinateAtPosition("a6")));

        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a5")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("a4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t7.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testLegalsMate() {

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
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g6")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g4"),
                        BoardUtils.getCoordinateAtPosition("d1")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e8"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t13 = t12.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c3"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        assertEquals(t13.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t13.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testSevenMoveMate() {

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
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("c7")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("h1")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t13 = t12.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("d8")));

        assertEquals(t13.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t13.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testGrecoGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));

        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("d2")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f6"),
                        BoardUtils.getCoordinateAtPosition("g4")));


        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h2"),
                        BoardUtils.getCoordinateAtPosition("h3")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g4"),
                        BoardUtils.getCoordinateAtPosition("e3")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("e3")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h4"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t12.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testOlympicGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c7"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertEquals(t2.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d5"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c3"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("d7")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t11.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testAnotherGame() {

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
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g5"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h1"),
                        BoardUtils.getCoordinateAtPosition("f1")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t13 = t12.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t13.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t14 = t13.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t13.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t14.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t14.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testSmotheredMate() {

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
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t8.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testHippopotamusMate() {

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
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t3.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

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
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h4"),
                        BoardUtils.getCoordinateAtPosition("g5")));


        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t12.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testBlackburneShillingMate() {

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
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertEquals(t4.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertEquals(t5.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertEquals(t6.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertEquals(t7.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertEquals(t8.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertEquals(t9.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g5"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertEquals(t10.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h1"),
                        BoardUtils.getCoordinateAtPosition("f1")));

        assertEquals(t11.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertEquals(t12.getMoveStatus(),MoveStatus.DONE);

        final MoveTransition t13 = t12.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertEquals(t13.getMoveStatus(),MoveStatus.DONE);;

        final MoveTransition t14 = t13.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t13.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertEquals(t14.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t14.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testAnastasiaMate() {

        final Board.Builder builder = new Board.Builder();

        // Black Layout
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Rook(5,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new King(15,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Knight(12,Alliance.WHITE));
        builder.setPiece(new Rook(27,Alliance.WHITE));
        builder.setPiece(new Pawn(41,Alliance.WHITE));
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new King(62,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d5"),
                        BoardUtils.getCoordinateAtPosition("h5")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testTwoBishopMate() {

        final Board.Builder builder = new Board.Builder();

        builder.setPiece(new King(7,Alliance.BLACK, false, false));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));
        builder.setPiece(new Pawn(17,Alliance.BLACK));
        // White Layout
        builder.setPiece(new Bishop(40,Alliance.WHITE));
        builder.setPiece(new Bishop(48,Alliance.WHITE));
        builder.setPiece(new King(53,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a3"),
                        BoardUtils.getCoordinateAtPosition("b2")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenRookMate() {

        final Board.Builder builder = new Board.Builder();

        // Black Layout
        builder.setPiece(new King(5,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Rook(9,Alliance.WHITE));
        builder.setPiece(new Queen(16,Alliance.WHITE));
        builder.setPiece(new King(59,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a8")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testQueenKnightMate() {

        final Board.Builder builder = new Board.Builder();

        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        // White Layout
        builder.setPiece(new Queen(15,Alliance.WHITE));
        builder.setPiece(new Knight(29,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("h7"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }

    @Test
    public void testBackRankMate() {

        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        builder.setPiece(new Rook(18,Alliance.BLACK));
        // White Layout
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new King(62,Alliance.WHITE, false, false));
        // Set the current player
        builder.setNextMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("c1")));

        assertEquals(t1.getMoveStatus(),MoveStatus.DONE);
        assertTrue(t1.getTransitionBoard().getCurrentPlayer().isInCheckMate());

    }


}