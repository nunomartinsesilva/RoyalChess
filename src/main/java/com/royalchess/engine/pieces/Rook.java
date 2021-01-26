package com.royalchess.engine.pieces;

import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Rook extends Piece{

    private final static int [] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

    public Rook( final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.ROOK, piecePosition, pieceAlliance,true);
    }

    public Rook(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(PieceType.ROOK, piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;

                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else{
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(this.pieceAlliance!= pieceAlliance) {
                            legalMoves.add(new Move.MajorAttackMove(board,this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }

                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance(),false);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.A_COLUMN[currentPosition] && (candidateOffset ==-1);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.H_COLUMN[currentPosition] && (candidateOffset ==1);
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
