package api.chess.gameplay.rules;

import java.util.List;

import api.chess.equipment.board.Board;
import api.chess.equipment.pieces.Piece;

public enum Move {
	BASIC_MOVE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	CAPTURE_MOVE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	STRAIGHT {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	STRAIGHT_ONE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	DIAGONAL {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	DIAGONAL_ONE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	KNIGHT_JUMP {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PAWN_MOVE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PAWN_FIRST_MOVE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PAWN_CAPTURE {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PROMOTION {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	CASTELING {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	EN_PASSANT {

		@Override
		public List<Turn> evaluate(Board board, Piece piece) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public abstract List<Turn> evaluate(Board board, Piece piece);
}
