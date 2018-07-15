package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.chess.player.Player;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Bishop extends Piece {
    private final transient static Logger LOG = Logger.getLogger(Bishop.class.getName());

    @Override
    public Piece init(int id, PieceConfig.Color color) {
        name = PieceConfig.PieceName.BISHOP;
        super.init(id, color);
        positionSquareId = initPosition(new Coordinates(2, 0), id, 3);
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
