package com.royalchess.engine.pieces;

import com.royalchess.engine.player.Alliance;
import com.royalchess.engine.board.Board;
import com.royalchess.engine.move.Move;

import java.util.Collection;


public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    @Override
    public boolean equals(final Object other) {
       if(this == other) {
           return true;
       }
       if(!(other instanceof Piece)) {
           return false;
       }
       final Piece otherPiece = (Piece) other;
       return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
               && pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    private int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pieceType.hashCode();
        result = prime * result + pieceAlliance.hashCode();
        result = prime * result + piecePosition;
        result = prime * result + (isFirstMove ? 1 : 0);
        return result;

    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Alliance getPieceAlliance() {

        return this.pieceAlliance;
    }
    public int getPiecePosition(){

        return this.piecePosition;
    }
    public int getPieceValue () {
        return this.pieceType.getPieceValue();
    }

    public boolean isFirstMove() {
        return  this.isFirstMove;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);

    public enum PieceType {
        KING("K",10000),
        QUEEN("Q",900),
        ROOK("R",500),
        BISHOP("B",300),
        KNIGHT("N",300),
        PAWN("P",100);

        private String pieceName;
        private int pieceValue;

        PieceType (final String pieceName, final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
        public int getPieceValue() {
            return this.pieceValue;
        }
    }
}
