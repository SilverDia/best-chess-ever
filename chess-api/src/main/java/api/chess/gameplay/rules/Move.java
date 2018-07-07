package api.chess.gameplay.rules;

import java.util.ArrayList;
import java.util.List;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.equipment.board.Direction;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.Rook;
import api.config.BoardConfig;
import api.config.PieceConfig;

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
			List<Movement> moves = new ArrayList<>();
			moves.addAll(MoveUtils.evaluateCombination(this, false, new Direction(1, 2), board, piece));
			moves.addAll(MoveUtils.evaluateCombination(this, false, new Direction(-1, 2), board, piece));
			return moves;
		}
	},
	PAWN_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateDirection(this, false,
					piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT : Direction.VERT.invert(), board,
					piece);
		}
	},
	PAWN_FIRST_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			if (!piece.hasMoved())
				return MoveUtils.evaluateDirection(this, false, null,
						piece.getColor().equals(PieceConfig.Color.WHITE) ? new Direction(0, 2) : new Direction(0, -2),
						board, piece, 1);
			return new ArrayList<>();
		}
	},
	PAWN_CAPTURE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			Coordinates rt_coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(
					piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_RT : Direction.DIAG_RT.invert(),
					1);
			Coordinates lt_coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(
					piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_LT : Direction.DIAG_LT.invert(),
					1);

			if (rt_coord.isValid()) {
				Piece diag_rt = board.getSquare(BoardConfig.toSquareId(rt_coord)).getPiece();
				if (diag_rt != null && diag_rt.getColor() != piece.getColor())
					moves.add(new Movement(piece.getPositionSquareId(), diag_rt.getPositionSquareId(),
							piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_RT
									: Direction.DIAG_RT.invert(),
							this, null).addMovementRule(CAPTURE_MOVE));
			}

			if (lt_coord.isValid()) {
				Piece diag_lt = board.getSquare(BoardConfig.toSquareId(lt_coord)).getPiece();
				if (diag_lt != null && diag_lt.getColor() != piece.getColor())
					moves.add(new Movement(piece.getPositionSquareId(), diag_lt.getPositionSquareId(),
							piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_LT
									: Direction.DIAG_LT.invert(),
							this, null).addMovementRule(CAPTURE_MOVE));
			}

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
				if (!right_side.isEmpty()) {
				Piece rightOutmost = board.getSquare(right_side.get(right_side.size() - 1).getMoveToSquareId())
						.getPiece();

				if (rightOutmost != null && rightOutmost instanceof Rook && !rightOutmost.hasMoved()
						&& right_side.get(right_side.size() - 1).getBlockedBy() == null)
					moves.add(right_side.get(1));
				}

				List<Movement> left_side = MoveUtils.evaluateDirection(this, true, null, Direction.HOR.invert(), board,
						piece, 1);
				if (!left_side.isEmpty()) {
				Piece leftOutmost = board.getSquare(left_side.get(left_side.size() - 1).getMoveToSquareId()).getPiece();

				if (leftOutmost != null && leftOutmost instanceof Rook && !leftOutmost.hasMoved()
						&& left_side.get(left_side.size() - 1).getBlockedBy() == null)
					moves.add(left_side.get(1));
				}
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

	public List<Movement> evaluateDirection(Board board, Piece piece, Direction dir) {
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

			Coordinates newCoords = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(direction, step);

			if (newCoords.isValid()) {
				Square next = board.getSquare(BoardConfig.toSquareId(newCoords));
				if (next.getPiece() == null) {
					moves.add(
							new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy));
					return continueAfter
							? addLists(moves,
									evaluateDirection(move, continueAfter, blockedBy, direction, board, piece, ++step))
							: moves;
				}

				if (!next.getPiece().getColor().equals(piece.getColor())) {
					moves.add(new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy)
							.addMovementRule(Move.CAPTURE_MOVE));
					blockedBy = (blockedBy == null) ? next.getPiece() : blockedBy;
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
