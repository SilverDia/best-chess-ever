package api.config;

import api.chess.equipment.board.Coordinates;

import java.util.logging.Logger;

public class BoardConfig {
    private final static Logger LOG = Logger.getLogger(BoardConfig.class.getName());

    public static enum Direction {
        DIAG_LT, VERT, DIAG_RT, HOR
    }

    public static String toSquareId(int x, int y) {
        String rankId = String.valueOf((char) (x + 65));
        String fileId = String.valueOf((char) (y + 49));
        return rankId + fileId;
    }

    public static String toSquareId(Coordinates coordinates) {
        return toSquareId(coordinates.getX(), coordinates.getY());
    }
}
