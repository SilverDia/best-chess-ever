package api.chess.equipment.pieces;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.gameplay.game.Game;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Movement.Restriction;
import api.chess.player.Player;
import api.config.GameConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class King extends Piece {
	private final transient static Logger LOG = Logger.getLogger(King.class.getName());
	private transient Movement checkedByMove;

	@Override
	public Piece init(int id, PieceConfig.Color color) {
		name = PieceConfig.PieceName.KING;
		super.init(id, color);
		positionSquareId = initPosition(new Coordinates(3, 0), id, 1);
		return this;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public Game.GameState evaluateCheck(Board board, List<Movement> enemyMovements) {
		getPossibleMoves().removeAll(GameConfig.intersectLists(getPossibleMoves(), enemyMovements,
				move -> !Restriction.NO_CAPTURE.equals(move.getRestriction())
						&& (move.getBlockedBy() == null || move.getBlockedBy() == this)));
		List<Movement> movesAimingOnKing = enemyMovements.stream()
				.filter(move -> move.getMoveToSquareId().equals(getPositionSquareId())).collect(Collectors.toList());

		checkedByMove = movesAimingOnKing.stream().filter(move -> move.getBlockedBy() == null).findAny().orElse(null);

		if (checkedByMove != null) {
			getPossibleMoves().removeAll(
					getPossibleMoves().stream().filter(move -> Restriction.RESTRICT_KING.equals(move.getRestriction()))
							.collect(Collectors.toList()));
			if (getPossibleMoves().isEmpty())
				return Game.GameState.CHECKMATE;
			return Game.GameState.CHECK;
		}

		if (movesAimingOnKing.isEmpty())
			return Game.GameState.CLEAR;

		movesAimingOnKing.forEach(move -> move.getBlockedBy().limitMoves(board, move));
		return Game.GameState.CLEAR;
	}

	public Movement getCheckMove() {
		return checkedByMove;
	}
}
