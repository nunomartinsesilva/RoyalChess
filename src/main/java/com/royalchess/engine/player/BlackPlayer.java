package com.royalchess.engine.player;

import com.royalchess.engine.board.Board;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.board.Tile;
import com.royalchess.engine.pieces.Piece;
import com.royalchess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves, final Collection<Move> opponentLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck() ) {
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5,opponentLegalMoves).isEmpty() && Player.calculateAttacksOnTile(6,opponentLegalMoves).isEmpty()
                            && rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing,6 , (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(),5));
                    }
                }
            }
            if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(1,opponentLegalMoves).isEmpty() && Player.calculateAttacksOnTile(2,opponentLegalMoves).isEmpty()
                            && Player.calculateAttacksOnTile(3,opponentLegalMoves).isEmpty()&& rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing,2 , (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(),3));
                    }
                }
            }

        }

        //return Collections.unmodifiableList(kingCastles);
        return kingCastles;

    }
}
