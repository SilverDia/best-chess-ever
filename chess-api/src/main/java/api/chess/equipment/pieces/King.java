package api.chess.equipment.pieces;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.gameplay.game.GameState;
import api.chess.gameplay.rules.Movement;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

	public GameState evaluateCheck(Board board, List<Movement> enemyMovements) {
		intersectLists(true, getPossibleMoves(), enemyMovements);
		List<Movement> movesAimingOnKing = enemyMovements.stream()
				.filter(move -> move.getMoveToSquareId().equals(getPositionSquareId())).collect(Collectors.toList());

		if (movesAimingOnKing.stream().filter(move -> move.getBlockedBy() == null).count() > 0) {
			if (getPossibleMoves().isEmpty())
				return GameState.CHECKMATE;
			return GameState.CHECK;
		}

		if (movesAimingOnKing.isEmpty())
			return GameState.CLEAR;

		movesAimingOnKing.forEach(move -> move.getBlockedBy().limitMoves(board, move));
		return GameState.CLEAR;
	}
}
