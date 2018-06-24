package api.chess.equipment.pieces;

import api.config.PieceConfig;

import java.util.logging.Logger;

public class Pawn extends Piece{
    private final static Logger LOG = Logger.getLogger(Pawn.class.getName());

    public Pawn(String id, PieceConfig.PieceName name, PieceConfig.Color color) {
        super(id, name, color);
    }
}
