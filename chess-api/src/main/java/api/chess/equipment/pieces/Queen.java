package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.chess.player.Player;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Queen extends Piece {
	private final transient static Logger LOG = Logger.getLogger(Queen.class.getName());

	@Override
	public Piece init(int id, PieceConfig.Color color) {
		name = PieceConfig.PieceName.QUEEN;
		super.init(id, color);
		positionSquareId = initPosition(new Coordinates(4, 0), id, 1);
		return this;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
