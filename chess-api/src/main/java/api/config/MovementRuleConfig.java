package api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MovementRuleConfig {
    private final static Logger LOG = Logger.getLogger(MovementRuleConfig.class.getName());

    public enum Move {
        STRAIGHT, STRAIGHT_ONE, DIAGONAL, DIAGONAL_ONE,  KNIGHT_JUMP, PAWN_MOVE, PAWN_FIRST_MOVE, PAWN_CAPTURE, PROMOTION, CASTELING, EN_PASSANT
    }

    public static ArrayList<Move> getMoves(PieceConfig.PieceName pieceName) {
        ArrayList<Move> moves = new ArrayList<>();
        switch (pieceName) {
            case PAWN: {
                moves.add(Move.PAWN_MOVE);
                moves.add(Move.PAWN_FIRST_MOVE);
                moves.add(Move.PAWN_CAPTURE);
                moves.add(Move.PROMOTION);
                break;
            }
            case ROOK: {
                moves.add(Move.STRAIGHT);
                moves.add(Move.CASTELING);
                break;
            }
            case KNIGHT: {
                moves.add(Move.KNIGHT_JUMP);
                break;
            }
            case BISHOP: {
                moves.add(Move.STRAIGHT);
                break;
            }
            case QUEEN: {
                moves.add(Move.STRAIGHT);
                moves.add(Move.DIAGONAL);
                break;
            }
            case KING: {
                moves.add(Move.STRAIGHT_ONE);
                moves.add(Move.DIAGONAL_ONE);
                moves.add(Move.CASTELING);
                break;
            }
        }
        return moves;
    }
}
