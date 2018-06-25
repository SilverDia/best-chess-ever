package api.chess.equipment.pieces;

import api.config.PieceConfig;

import java.util.logging.Logger;

public class Pawn extends Piece {
    private final static Logger LOG = Logger.getLogger(Pawn.class.getName());

    public Pawn(int id, PieceConfig.Color color) {
        this.name = PieceConfig.PieceName.PAWN;
        this.color = color;
        this.id = name.toString() + "_" + color.toString() + "_" + id;
    }
}
