package api.chess.gameplay.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.equipment.board.Direction;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Pawn;
import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.Rook;
import api.config.BoardConfig;
import api.config.PieceConfig;

public enum Move {
	CAPTURE_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return null;
		}
	},
	STRAIGHT {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, 8, Direction.HOR, board, piece);
		}
	},
	STRAIGHT_ONE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, 1, Direction.HOR, board, piece);
		}
	},
	DIAGONAL {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, 8, Direction.DIAG_RT, board, piece);
		}
	},
	DIAGONAL_ONE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateCombination(this, 1, Direction.DIAG_RT, board, piece);
		}
	},
	KNIGHT_JUMP {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			moves.addAll(MoveUtils.evaluateCombination(this, 1, new Direction(1, 2), board, piece));
			moves.addAll(MoveUtils.evaluateCombination(this, 1, new Direction(-1, 2), board, piece));
			return moves;
		}
	},
	PAWN_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			return MoveUtils.evaluateDirection(this, 1,
					piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT : Direction.VERT.invert(), board,
					piece);
		}
	},
	PAWN_FIRST_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			if (!piece.hasMoved()) {
				List<Movement> result = MoveUtils.evaluateDirection(this, 2, null,
						piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT : Direction.VERT.invert(),
						board, piece, 1);
				if (result.size() == 2 && result.get(2).getBlockedBy() == null)
					return Arrays.asList(result.get(2));
			}
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
					moves.add(new Movement(piece.getPositionSquareId(),
							diag_rt.getPositionSquareId(), piece.getColor().equals(PieceConfig.Color.WHITE)
									? Direction.DIAG_RT : Direction.DIAG_RT.invert(),
							this, null).addMovementRule(CAPTURE_MOVE));
			}

			if (lt_coord.isValid()) {
				Piece diag_lt = board.getSquare(BoardConfig.toSquareId(lt_coord)).getPiece();
				if (diag_lt != null && diag_lt.getColor() != piece.getColor())
					moves.add(new Movement(piece.getPositionSquareId(),
							diag_lt.getPositionSquareId(), piece.getColor().equals(PieceConfig.Color.WHITE)
									? Direction.DIAG_LT : Direction.DIAG_LT.invert(),
							this, null).addMovementRule(CAPTURE_MOVE));
			}

			return moves;
		}
	},

	PROMOTION {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			int promotionY = piece.getColor().equals(PieceConfig.Color.WHITE) ? 7 : 0;
			if (piece.getCoords(board).getY() == promotionY)
				moves.add(new Movement(piece.getPositionSquareId(), piece.getPositionSquareId(), new Direction(0, 0),
						this, null));
			return moves;
		}
	},

	CASTELING {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();

			if (!piece.hasMoved()) {
				List<Movement> right_side = MoveUtils.evaluateDirection(this, 5, null, Direction.HOR, board, piece,
						1);
				if (!right_side.isEmpty()) {
					Piece rightOutmost = board.getSquare(right_side.get(right_side.size() - 1).getMoveToSquareId())
							.getPiece();

					if (rightOutmost != null && rightOutmost instanceof Rook && !rightOutmost.hasMoved()
							&& right_side.get(right_side.size() - 1).getBlockedBy() == null)
						moves.add(right_side.get(1));
				}

				List<Movement> left_side = MoveUtils.evaluateDirection(this, 5, null, Direction.HOR.invert(), board,
						piece, 1);
				if (!left_side.isEmpty()) {
					Piece leftOutmost = board.getSquare(left_side.get(left_side.size() - 1).getMoveToSquareId())
							.getPiece();

					if (leftOutmost != null && leftOutmost instanceof Rook && !leftOutmost.hasMoved()
							&& left_side.get(left_side.size() - 1).getBlockedBy() == null)
						moves.add(left_side.get(1));
				}
			}

			return moves;
		}
	},

	EN_PASSANT {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();

			Coordinates rCoord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(Direction.HOR, 1);
			if (rCoord.isValid()) {
				Piece rPiece = board.getSquare(BoardConfig.toSquareId(rCoord)).getPiece();
				Turn lastTurn = board.getGame().getTurnHistory().getLast();
				if (rPiece != null && !rPiece.getColor().equals(piece.getColor())
						&& lastTurn.getMovement().getRules().contains(PAWN_FIRST_MOVE)
						&& board.getSquare(lastTurn.getMovement().getMoveToSquareId()).getPiece() == rPiece)
					moves.add(new Movement(piece.getPositionSquareId(), rPiece.getPositionSquareId(), Direction.HOR,
							this, null).addMovementRule(CAPTURE_MOVE));
			}

			Coordinates lCoord = board.getSquare(piece.getPositionSquareId()).getCoordinates()
					.next(Direction.HOR.invert(), 1);
			if (lCoord.isValid()) {
				Piece lPiece = board.getSquare(BoardConfig.toSquareId(lCoord)).getPiece();
				Turn lastTurn = board.getGame().getTurnHistory().getLast();
				if (lPiece != null && !lPiece.getColor().equals(piece.getColor())
						&& lastTurn.getMovement().getRules().contains(PAWN_FIRST_MOVE)
						&& board.getSquare(lastTurn.getMovement().getMoveToSquareId()).getPiece() == lPiece)
					moves.add(new Movement(piece.getPositionSquareId(), lPiece.getPositionSquareId(), Direction.HOR,
							this, null).addMovementRule(CAPTURE_MOVE));
			}

			return moves;
		}
	};

	public abstract List<Movement> evaluate(Board board, Piece piece);

	public List<Movement> evaluateDirection(Board board, Piece piece, Direction dir) {
		return MoveUtils.evaluateDirection(this, 8, null, dir, board, piece, 1);
	}

	private static class MoveUtils {
		private static List<Movement> evaluateDirection(Move move, int maxSteps, Direction direction,
				Board board, Piece piece) {
			return evaluateDirection(move, maxSteps, null, direction, board, piece, 1);
		}

		private static List<Movement> evaluateDirection(Move move, int maxSteps, Piece blockedBy,
				Direction direction, Board board, Piece piece, int step) {
			List<Movement> moves = new ArrayList<>();

			Coordinates newCoords = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(direction, step);

			if (newCoords.isValid()) {
				Square next = board.getSquare(BoardConfig.toSquareId(newCoords));
				if (next.getPiece() == null) {
					moves.add(
							new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy));
					return --maxSteps > 0
							? addLists(moves,
									evaluateDirection(move, maxSteps, blockedBy, direction, board, piece, ++step))
							: moves;
				}

				if (!next.getPiece().getColor().equals(piece.getColor())) {
					moves.add(new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy)
							.addMovementRule(Move.CAPTURE_MOVE));
					blockedBy = (blockedBy == null) ? next.getPiece() : blockedBy;
					return --maxSteps > 0
							? addLists(moves,
									evaluateDirection(move, maxSteps, blockedBy, direction, board, piece, ++step))
							: moves;
				}
			}
			return moves;
		}

		private static List<Movement> evaluateCombination(Move move, int maxSteps, Direction direction,
				Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			moves.addAll(evaluateDirection(move, maxSteps, direction, board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.invert(), board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.rotate(), board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.rotate().invert(), board, piece));
			return moves;
		}

		private static <T> List<T> addLists(List<T> l1, List<T> l2) {
			l1.addAll(l2);
			return l1;
		}
	}
}
