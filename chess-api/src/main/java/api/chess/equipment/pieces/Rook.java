package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.chess.player.Player;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Rook extends Piece {
	private final transient static Logger LOG = Logger.getLogger(Rook.class.getName());

	@Override
	public Piece init(int id, PieceConfig.Color color) {
		name = PieceConfig.PieceName.ROOK;
		super.init(id, color);
		positionSquareId = initPosition(new Coordinates(0, 0), id, 7);
		return this;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
