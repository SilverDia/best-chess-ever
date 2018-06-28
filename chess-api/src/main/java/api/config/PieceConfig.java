package api.config;

import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.PieceSet;
import api.chess.gameplay.game.Game;

import java.util.logging.Logger;

import static api.config.GameConfig.RESOURCE_PATH_IMAGES_PIECESETS;

public class PieceConfig {
    private final static Logger LOG = Logger.getLogger(PieceConfig.class.getName());

    public enum PieceName {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    public enum Color {
        BLACK, WHITE
    }

    public static String buildImageUrl(Piece piece, PieceSet pieceSet) {
        return RESOURCE_PATH_IMAGES_PIECESETS
                + Game.pieceSet.toString().toLowerCase()
                + "/"
                + piece.getName().toString().toLowerCase()
                + "_"
                + piece.getColor().toString().toLowerCase()
                + ".png";
    }
}
