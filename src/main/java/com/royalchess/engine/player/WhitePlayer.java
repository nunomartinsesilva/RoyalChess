package com.royalchess.engine.player;

import com.royalchess.engine.board.Board;
import com.royalchess.engine.move.Move;
import com.royalchess.engine.board.Tile;
import com.royalchess.engine.pieces.Piece;
import com.royalchess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board,whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves, final Collection<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck() ) {
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61,opponentLegalMoves).isEmpty() && Player.calculateAttacksOnTile(62,opponentLegalMoves).isEmpty()
                    && rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing,62 , (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(),61));
                    }
                }
            }
            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(58,opponentLegalMoves).isEmpty() && Player.calculateAttacksOnTile(57,opponentLegalMoves).isEmpty()
                    && Player.calculateAttacksOnTile(59,opponentLegalMoves).isEmpty()&& rookTile.getPiece().getPieceType() == Piece.PieceType.ROOK) {
                        kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing,58 , (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(),59));
                    }
                }
            }

        }

        //return Collections.unmodifiableList(kingCastles);
        return kingCastles;



    }


}
