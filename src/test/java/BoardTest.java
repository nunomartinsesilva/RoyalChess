import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.pieces.*;

import com.royalchess.engine.move.MoveStatus;
import com.royalchess.engine.move.MoveTransition;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest{

    @Test
    public void initialBoardConstruction() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.getTile(0).getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(board.getTile(1).getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(board.getTile(2).getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(board.getTile(3).getPiece().getPieceType(), Piece.PieceType.QUEEN);
        assertEquals(board.getTile(4).getPiece().getPieceType(), Piece.PieceType.KING);
        assertEquals(board.getTile(5).getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(board.getTile(6).getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(board.getTile(7).getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(board.getTile(8).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(9).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(10).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(11).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(12).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(13).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(14).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(15).getPiece().getPieceType(), Piece.PieceType.PAWN);

        assertEquals(board.getTile(48).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(49).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(50).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(51).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(52).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(53).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(54).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(55).getPiece().getPieceType(), Piece.PieceType.PAWN);
        assertEquals(board.getTile(56).getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(board.getTile(57).getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(board.getTile(58).getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(board.getTile(59).getPiece().getPieceType(), Piece.PieceType.QUEEN);
        assertEquals(board.getTile(60).getPiece().getPieceType(), Piece.PieceType.KING);
        assertEquals(board.getTile(61).getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(board.getTile(62).getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(board.getTile(63).getPiece().getPieceType(), Piece.PieceType.ROOK);

    }

    @Test
    public void noPiecesMoved() {
        final Board board = Board.createStandardBoard();
        assertTrue(board.getTile(0).getPiece().isFirstMove());
        assertTrue(board.getTile(1).getPiece().isFirstMove());
        assertTrue(board.getTile(2).getPiece().isFirstMove());
        assertTrue(board.getTile(3).getPiece().isFirstMove());
        assertTrue(board.getTile(4).getPiece().isFirstMove());
        assertTrue(board.getTile(5).getPiece().isFirstMove());
        assertTrue(board.getTile(6).getPiece().isFirstMove());
        assertTrue(board.getTile(7).getPiece().isFirstMove());
        assertTrue(board.getTile(8).getPiece().isFirstMove());
        assertTrue(board.getTile(9).getPiece().isFirstMove());
        assertTrue(board.getTile(10).getPiece().isFirstMove());
        assertTrue(board.getTile(11).getPiece().isFirstMove());
        assertTrue(board.getTile(12).getPiece().isFirstMove());
        assertTrue(board.getTile(13).getPiece().isFirstMove());
        assertTrue(board.getTile(14).getPiece().isFirstMove());
        assertTrue(board.getTile(15).getPiece().isFirstMove());

        assertTrue(board.getTile(48).getPiece().isFirstMove());
        assertTrue(board.getTile(49).getPiece().isFirstMove());
        assertTrue(board.getTile(50).getPiece().isFirstMove());
        assertTrue(board.getTile(51).getPiece().isFirstMove());
        assertTrue(board.getTile(52).getPiece().isFirstMove());
        assertTrue(board.getTile(53).getPiece().isFirstMove());
        assertTrue(board.getTile(54).getPiece().isFirstMove());
        assertTrue(board.getTile(55).getPiece().isFirstMove());
        assertTrue(board.getTile(56).getPiece().isFirstMove());
        assertTrue(board.getTile(57).getPiece().isFirstMove());
        assertTrue(board.getTile(58).getPiece().isFirstMove());
        assertTrue(board.getTile(59).getPiece().isFirstMove());
        assertTrue(board.getTile(60).getPiece().isFirstMove());
        assertTrue(board.getTile(61).getPiece().isFirstMove());
        assertTrue(board.getTile(62).getPiece().isFirstMove());
        assertTrue(board.getTile(63).getPiece().isFirstMove());

    }

    @Test
    public void startingBoardLegalMovesCheck() {
        final Board board = Board.createStandardBoard();
        final Iterable<Move> allMoves = board.getAllLegalMoves();
        int moveCounter=0;
        for(final Move move: allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastlingMove());
            moveCounter++;
        }
        assertEquals(moveCounter,40);
    }

    @Test
    public void startingBoardEmptyTileCheck() {
        final Board board = Board.createStandardBoard();
        assertNull(board.getTile(16).getPiece());
        assertNull(board.getTile(20).getPiece());
        assertNull(board.getTile(35).getPiece());
        assertNull(board.getTile(41).getPiece());
        assertNull(board.getTile(47).getPiece());

    }

    @Test
    public void WhitePlayerCheck() {
        final Board board = Board.createStandardBoard();

        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().isCastled());
        assertTrue(board.getCurrentPlayer().isKingSideCastleCapable());
        assertTrue(board.getCurrentPlayer().isQueenSideCastleCapable());
        assertEquals(board.getCurrentPlayer().getLegalMoves().size(),20);
        assertEquals(board.getCurrentPlayer().getActivePieces().size(),16);
    }

    @Test
    public void BlackPlayerCheck() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.getCurrentPlayer().getOpponent(), board.getBlackPlayer());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheck());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.getCurrentPlayer().getOpponent().isCastled());
        assertTrue(board.getCurrentPlayer().getOpponent().isKingSideCastleCapable());
        assertTrue(board.getCurrentPlayer().getOpponent().isQueenSideCastleCapable());
        assertEquals(board.getCurrentPlayer().getOpponent().getLegalMoves().size(),20);
        assertEquals(board.getCurrentPlayer().getOpponent().getActivePieces().size(),16);
    }



    @Test
    public void testAlgebraicNotation() {
        assertEquals(BoardUtils.getPositionAtCoordinate(0), "a8");
        assertEquals(BoardUtils.getPositionAtCoordinate(1), "b8");
        assertEquals(BoardUtils.getPositionAtCoordinate(2), "c8");
        assertEquals(BoardUtils.getPositionAtCoordinate(3), "d8");
        assertEquals(BoardUtils.getPositionAtCoordinate(4), "e8");
        assertEquals(BoardUtils.getPositionAtCoordinate(5), "f8");
        assertEquals(BoardUtils.getPositionAtCoordinate(6), "g8");
        assertEquals(BoardUtils.getPositionAtCoordinate(7), "h8");
        assertEquals(BoardUtils.getPositionAtCoordinate(63),"h1");
    }

    @Test
    public void testReverseAlgebraicNotation() {
        assertEquals(BoardUtils.getCoordinateAtPosition("a8"),0);
        assertEquals(BoardUtils.getCoordinateAtPosition("b8"),1);
        assertEquals(BoardUtils.getCoordinateAtPosition("c8"),2);
        assertEquals(BoardUtils.getCoordinateAtPosition("d8"),3);
        assertEquals(BoardUtils.getCoordinateAtPosition("e8"),4);
        assertEquals(BoardUtils.getCoordinateAtPosition("f8"),5);
        assertEquals(BoardUtils.getCoordinateAtPosition("g8"),6);
        assertEquals(BoardUtils.getCoordinateAtPosition("h8"),7);
        assertEquals(BoardUtils.getCoordinateAtPosition("h1"),63);
    }

    @Test
    public void testAlgebraicNotationOutOfBounds(){
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> BoardUtils.getPositionAtCoordinate(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> BoardUtils.getPositionAtCoordinate(64));
        assertThrows(NullPointerException.class, () -> BoardUtils.getCoordinateAtPosition("i1"));

    }


    @Test
    public void testPlainKingMove() {

        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(4,Alliance.BLACK, false, false));
        builder.setPiece(new Pawn(12,Alliance.BLACK));
        // White Layout
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE, false, false));
        builder.setNextMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        assertEquals(board.getWhitePlayer().getLegalMoves().size(), 6);
        assertEquals(board.getBlackPlayer().getLegalMoves().size(), 6);
        assertFalse(board.getCurrentPlayer().isInCheck());
        assertFalse(board.getCurrentPlayer().isInCheckMate());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheck());
        assertFalse(board.getCurrentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());
        assertEquals(board.getCurrentPlayer().getOpponent(), board.getBlackPlayer());

        final Move move = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e1"), BoardUtils.getCoordinateAtPosition("f1"));

        final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);

        assertEquals(moveTransition.getMove(), move);
        assertEquals(moveTransition.getTransitionBoard().getCurrentPlayer(), moveTransition.getTransitionBoard().getBlackPlayer());
        assertEquals(moveTransition.getMoveStatus(), MoveStatus.DONE);
        assertEquals(moveTransition.getTransitionBoard().getWhitePlayer().getPlayerKing().getPiecePosition(), 61);

    }


    @Test
    public void testBoardConsistency() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.getCurrentPlayer(), board.getWhitePlayer());

        final MoveTransition t1 = board.getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f7"),
                        BoardUtils.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t10.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h5"),
                        BoardUtils.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t11.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("f6"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t12.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h4"),
                        BoardUtils.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.getTransitionBoard()
                .getCurrentPlayer()
                .makeMove(Move.MoveFactory.createMove(t13.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d5"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t14.getTransitionBoard().getWhitePlayer().getActivePieces().size() == calculatedActivesFor(t14.getTransitionBoard(), Alliance.WHITE));
        assertTrue(t14.getTransitionBoard().getBlackPlayer().getActivePieces().size() == calculatedActivesFor(t14.getTransitionBoard(), Alliance.BLACK));

    }

    @Test
    public void testInvalidBoard() {
        final Board.Builder builder = new Board.Builder();
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setNextMoveMaker(Alliance.WHITE);
        Exception e = assertThrows(RuntimeException.class, () -> builder.build());
        assertEquals("No King found", e.getMessage());
    }

    private static int calculatedActivesFor(final Board board, final Alliance alliance) {

        if(alliance == Alliance.BLACK) {
            return board.getBlackPieces().size();
        }

        else if(alliance == Alliance.WHITE) {
            return board.getWhitePieces().size();
        }


        return 0;
    }

}