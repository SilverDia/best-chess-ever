package api.chess.gameplay.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import api.chess.equipment.board.*;
import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.Rook;
import api.chess.gameplay.rules.Movement.Restriction;
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
			List<Movement> moves = new ArrayList<>();
			Direction dir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT : Direction.VERT.invert();
			Coordinates coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(dir, 1);
			if (coord.isValid()) {
				Piece next = board.getSquare(BoardConfig.toSquareId(coord)).getPiece();
				if (next == null)
					moves.add(new Movement(piece.getPositionSquareId(), BoardConfig.toSquareId(coord), dir, this, null)
							.restrict(Restriction.NO_CAPTURE));
			}
			return moves;
		}
	},
	PAWN_FIRST_MOVE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			if (!piece.hasMoved()) {
				Direction dir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT
						: Direction.VERT.invert();
				Coordinates coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(dir, 1);
				Coordinates coord2 = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(dir, 2);

				if (coord.isValid() && coord2.isValid()) {
					Piece next = board.getSquare(BoardConfig.toSquareId(coord)).getPiece();
					Piece next2 = board.getSquare(BoardConfig.toSquareId(coord2)).getPiece();
					if (next == null && next2 == null)
						moves.add(new Movement(piece.getPositionSquareId(), BoardConfig.toSquareId(coord2), dir, this,
								null).restrict(Restriction.NO_CAPTURE));
				}
			}
			return moves;
		}
	},
	PAWN_CAPTURE {

		@Override
		public List<Movement> evaluate(Board board, Piece piece) {
			List<Movement> moves = new ArrayList<>();
			Direction rt_dir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_RT
					: Direction.DIAG_RT.invert();
			Direction lt_dir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.DIAG_LT
					: Direction.DIAG_LT.invert();
			Coordinates rt_coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(rt_dir, 1);
			Coordinates lt_coord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(lt_dir, 1);

			if (rt_coord.isValid()) {
				Piece diag_rt = board.getSquare(BoardConfig.toSquareId(rt_coord)).getPiece();
				Movement move_rt = new Movement(piece.getPositionSquareId(), BoardConfig.toSquareId(rt_coord), rt_dir,
						this, null);
				if (diag_rt != null && diag_rt.getColor() != piece.getColor())
					move_rt.addMovementRule(CAPTURE_MOVE);
				else
					move_rt.restrict(Restriction.RESTRICT_KING);
				moves.add(move_rt);
			}

			if (lt_coord.isValid()) {
				Piece diag_lt = board.getSquare(BoardConfig.toSquareId(lt_coord)).getPiece();
				Movement move_lt = new Movement(piece.getPositionSquareId(), BoardConfig.toSquareId(lt_coord), lt_dir,
						this, null);
				if (diag_lt != null && diag_lt.getColor() != piece.getColor())
					move_lt.addMovementRule(CAPTURE_MOVE);
				else
					move_lt.restrict(Restriction.RESTRICT_KING);
				moves.add(move_lt);
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
				List<Movement> right_side = MoveUtils.evaluateDirection(this, 5, null, Direction.HOR, board, piece, 1);
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

			Turn lastTurn = board.getGame().getTurnHistory().getLast();
			if (lastTurn.getMovement() != null && lastTurn.getMovement().getRules().contains(PAWN_FIRST_MOVE)) {
				Direction pawnDir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT
						: Direction.VERT.invert();

				Coordinates rCoord = board.getSquare(piece.getPositionSquareId()).getCoordinates().next(Direction.HOR,
						1);
				if (rCoord.isValid()) {
					Piece rPiece = board.getSquare(BoardConfig.toSquareId(rCoord)).getPiece();

					if (rPiece != null && !rPiece.getColor().equals(piece.getColor())
							&& board.getSquare(lastTurn.getMovement().getMoveToSquareId()).getPiece() == rPiece)
						moves.add(new Movement(piece.getPositionSquareId(),
								BoardConfig.toSquareId(rCoord.next(pawnDir, 1)), pawnDir.add(Direction.HOR), this, null)
										.addMovementRule(Move.CAPTURE_MOVE));
				}

				Coordinates lCoord = board.getSquare(piece.getPositionSquareId()).getCoordinates()
						.next(Direction.HOR.invert(), 1);
				if (lCoord.isValid()) {
					Piece lPiece = board.getSquare(BoardConfig.toSquareId(lCoord)).getPiece();
					if (lPiece != null && !lPiece.getColor().equals(piece.getColor())
							&& board.getSquare(lastTurn.getMovement().getMoveToSquareId()).getPiece() == lPiece)
						moves.add(new Movement(piece.getPositionSquareId(),
								BoardConfig.toSquareId(lCoord.next(pawnDir, 1)), pawnDir.add(Direction.HOR), this, null)
										.addMovementRule(Move.CAPTURE_MOVE));
				}
			}
			return moves;
		}
	};

	public abstract List<Movement> evaluate(Board board, Piece piece);

	public List<Movement> evaluateDirection(Board board, Piece piece, Direction dir) {
		return MoveUtils.evaluateDirection(this, 8, null, dir, board, piece, 1);
	}

	public static class MoveUtils {
		public static List<Movement> evaluateDirection(Move move, int maxSteps, Direction direction, Board board,
				Piece piece) {
			return evaluateDirection(move, maxSteps, null, direction, board, piece, 1);
		}

		public static List<Movement> evaluateDirection(Move move, int maxSteps, Piece blockedBy, Direction direction,
				Board board, Piece piece, int step) {
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
				} else if (next.getPiece().getColor().equals(piece.getColor()) && blockedBy == null)
					moves.add(new Movement(piece.getPositionSquareId(), next.getSquareId(), direction, move, blockedBy)
							.restrict(Restriction.RESTRICT_KING));
			}
			return moves;
		}

		public static List<Movement> evaluateCombination(Move move, int maxSteps, Direction direction, Board board,
				Piece piece) {
			List<Movement> moves = new ArrayList<>();
			moves.addAll(evaluateDirection(move, maxSteps, direction, board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.invert(), board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.rotate(), board, piece));
			moves.addAll(evaluateDirection(move, maxSteps, direction.rotate().invert(), board, piece));
			return moves;
		}

		public static <T> List<T> addLists(List<T> l1, List<T> l2) {
			l1.addAll(l2);
			return l1;
		}
	}
}
