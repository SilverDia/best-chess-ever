package api.chess.gameplay.rules;

import java.util.ArrayList;
import java.util.List;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Direction;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.Rook;
import api.config.BoardConfig;

public enum Move {
	CAPTURE_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			return moves;
		}
	},
	STRAIGHT {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, true, Direction.HOR, board, piece);
		}
	},
	STRAIGHT_ONE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, false, Direction.HOR, board, piece);
		}
	},
	DIAGONAL {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, true, Direction.DIAG_RT, board, piece);
		}
	},
	DIAGONAL_ONE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, false, Direction.DIAG_RT, board, piece);
		}
	},
	KNIGHT_JUMP {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, false, new Direction(1, 2), board, piece);
		}
	},
	PAWN_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateDirection(this, false, Direction.VERT, board, piece);
		}
	},
	PAWN_FIRST_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			if (!piece.hasMoved())
				return MoveUtils.evaluateDirection(this, false, null, Direction.VERT, board, piece, 2);
			return new ArrayList<>();
		}
	},
	PAWN_CAPTURE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			Piece diag_rt = board
					.getSquare(BoardConfig.toSquareId(
							board.getSquare(piece.getPositionSquareId()).getCoordinates().next(Direction.DIAG_RT, 1)))
					.getPiece();
			Piece diag_lt = board
					.getSquare(BoardConfig.toSquareId(
							board.getSquare(piece.getPositionSquareId()).getCoordinates().next(Direction.DIAG_LT, 1)))
					.getPiece();

			if (diag_lt != null && diag_lt.getColor() != piece.getColor())
				moves.add(new Movement(piece.getPositionSquareId(), diag_lt.getPositionSquareId(), Direction.DIAG_LT,
						this, diag_lt).addMovementRule(CAPTURE_MOVE));
			if (diag_rt != null && diag_rt.getColor() != piece.getColor())
				moves.add(new Movement(piece.getPositionSquareId(), diag_rt.getPositionSquareId(), Direction.DIAG_RT,
						this, diag_rt).addMovementRule(CAPTURE_MOVE));

			return moves;
		}
	},
	/*
	 * PROMOTION {
	 * 
	 * @Override public List<Movement> evaluate(Board board, Piece piece) {
	 * List<Movement> moves = new ArrayList<>(); return moves; } },
	 */
	CASTELING {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();

			if (!piece.hasMoved()) {
				List<Movement> right_side = MoveUtils.evaluateDirection(this, true, null, Direction.HOR, board, piece,
						1);
				Piece rightOutmost = board.getSquare(right_side.get(right_side.size() - 1).getMoveToSquareId())
						.getPiece();

				if (rightOutmost != null && rightOutmost instanceof Rook && !rightOutmost.hasMoved()
						&& right_side.get(right_side.size() - 1).getBlockedBy() == null)
					moves.add(right_side.get(1));

				List<Movement> left_side = MoveUtils.evaluateDirection(this, true, null, Direction.HOR.invert(), board,
						piece, 1);
				Piece leftOutmost = board.getSquare(left_side.get(left_side.size() - 1).getMoveToSquareId()).getPiece();

				if (leftOutmost != null && leftOutmost instanceof Rook && !leftOutmost.hasMoved()
						&& left_side.get(left_side.size() - 1).getBlockedBy() == null)
					moves.add(left_side.get(1));
			}

			return moves;
		}
	},
	/*
	 * EN_PASSANT {
	 * 
	 * @Override public List<Movement> evaluate(Board board, Piece piece) {
	 * List<Movement> moves = new ArrayList<>(); return moves; } }
	 */;

	public abstract List<Movement> evaluate(Board board, Piece piece);
	
	public List<Movement> evaluateDirection(Board board, Piece piece, Direction dir){
		return MoveUtils.evaluateDirection(this, true, null, dir, board, piece, 1);
	}

	private static class MoveUtils {
		private static List<Movement> evaluateDirection(Move move, boolean continueAfter, Direction direction,
				Board board, Piece piece) {
			return evaluateDirection(move, continueAfter, null, direction, board, piece, 1);
		}

		private static List<Movement> evaluateDirection(Move move, boolean continueAfter, Piece blockedBy,
				Direction direction, Board board, Piece piece, int step) {
			List<Movement> moves = new ArrayList<>();
			Square next = board.getSquare(BoardConfig
					.toSquareId(board.getSquare(piece.getPositionSquareId()).getCoordinates().next(direction, step)));

			if (next.getCoordinates().isValid()) {
				if (next.getPiece() == null) {
					moves.add(
							new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy));
					return continueAfter
							? addLists(moves,
									evaluateDirection(move, continueAfter, blockedBy, direction, board, piece, ++step))
							: moves;
				}

				if (next.getPiece().getColor() != piece.getColor()) {
					blockedBy = (blockedBy == null) ? next.getPiece() : blockedBy;
					moves.add(new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy)
							.addMovementRule(Move.CAPTURE_MOVE));
					return continueAfter
							? addLists(moves,
									evaluateDirection(move, continueAfter, blockedBy, direction, board, piece, ++step))
							: moves;
				}
			}
			return moves;
		}

		private static List<Movement> evaluateCombination(Move move, boolean continueAfter, Direction direction,
				Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			moves.addAll(evaluateDirection(move, continueAfter, direction, board, piece));
			moves.addAll(evaluateDirection(move, continueAfter, direction.invert(), board, piece));
			moves.addAll(evaluateDirection(move, continueAfter, direction.rotate(), board, piece));
			moves.addAll(evaluateDirection(move, continueAfter, direction.rotate().invert(), board, piece));
			return moves;
		}

		private static <T> List<T> addLists(List<T> l1, List<T> l2) {
			l1.addAll(l2);
			return l1;
		}
	}
}
