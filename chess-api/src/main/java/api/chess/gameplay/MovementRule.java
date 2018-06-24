package api.chess.gameplay;

import api.chess.equipment.Board;
import api.chess.equipment.pieces.Piece;

public interface MovementRule {

    public enum Move {
        STRAIGHT, DIAGONAL
    }

    public enum SpecialMove {
        KNIGHT_JUMP, PAWN_MOVE, PAWN_FIRST_MOVE, PAWN_CAPTURE, CASTELING, EN_PASSANT
    }

    public static void evaluateRule(Piece piece, Board board) {}
}
