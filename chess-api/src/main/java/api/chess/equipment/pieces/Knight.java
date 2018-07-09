package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.chess.player.Player;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Knight extends Piece {
    private final transient static Logger LOG = Logger.getLogger(Knight.class.getName());

    @Override
    public void init(int id, PieceConfig.Color color) {
        name = PieceConfig.PieceName.KNIGHT;
        super.init(id, color);
        positionSquareId = initPosition(new Coordinates(1, 0), id, 5);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
