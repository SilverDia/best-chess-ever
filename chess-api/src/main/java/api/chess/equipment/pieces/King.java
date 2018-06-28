package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class King extends Piece {
    private final transient static Logger LOG = Logger.getLogger(King.class.getName());

    @Override
    public void init(int id, PieceConfig.Color color) {
        name = PieceConfig.PieceName.KING;
        super.init(id, color);
        positionSquareId = initPosition(new Coordinates(3, 0), id, 1);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
