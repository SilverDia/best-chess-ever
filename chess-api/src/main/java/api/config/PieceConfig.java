package api.config;

import api.chess.gameplay.DefaultRule;

import java.util.logging.Logger;

public class PieceConfig {
    private final static Logger LOG = Logger.getLogger(PieceConfig.class.getName());

    public enum PieceName {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    public enum Color {
        BLACK, WHITE
    }

    public static DefaultRule buildMovementRule(PieceName pieceName) {

        return new DefaultRule();
    }
}
