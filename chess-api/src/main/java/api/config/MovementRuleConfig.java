package api.config;

import java.util.ArrayList;
import java.util.logging.Logger;

import api.chess.gameplay.rules.Move;

public class MovementRuleConfig {
    private final static Logger LOG = Logger.getLogger(MovementRuleConfig.class.getName());

    public static ArrayList<Move> getMoves(PieceConfig.PieceName pieceName) {
        ArrayList<Move> moves = new ArrayList<>();
        switch (pieceName) {
            case PAWN: {
                moves.add(Move.PAWN_MOVE);
                moves.add(Move.PAWN_FIRST_MOVE);
                moves.add(Move.PAWN_CAPTURE);
                break;
            }
            case ROOK: {
                moves.add(Move.STRAIGHT);
                break;
            }
            case KNIGHT: {
                moves.add(Move.KNIGHT_JUMP);
                break;
            }
            case BISHOP: {
                moves.add(Move.DIAGONAL);
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
