package api.config;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.rules.Move;
import api.chess.gameplay.rules.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MovementRuleConfig {
    private final static Logger LOG = Logger.getLogger(MovementRuleConfig.class.getName());

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

    public static void evaluateMovement(Piece piece, Board board) {
        for (Move move : piece.getMoves()) {
            if (move.toString().startsWith("STRAIGHT")) {

            }
        }
    }

    public static void evaluateStraightMove(Piece piece, Board board, boolean limitedSteps) {
        boolean continueEvaluation = limitedSteps;
        int x_start = BoardConfig.toCoordinates(piece.getPositionSquareId()).getX();
        int y_start = BoardConfig.toCoordinates(piece.getPositionSquareId()).getY();
        int steps = 1;
        do {
            Square squareToTest = board.getSquare(x_start + steps, y_start + steps);
            steps++;
            if (squareToTest.isVacant()) {
                piece.addPossibleMove(new Movement(piece.getPositionSquareId(), squareToTest.getSquareId(), Move.BASIC_MOVE));
            } else if (squareToTest.getPieceId().contains(piece.getColor().toString())) {
                continueEvaluation = false;
            } else {
                Movement movement = new Movement(piece.getPositionSquareId(), squareToTest.getSquareId(), Move.BASIC_MOVE);
                movement.addMovementRule(Move.CAPTURE_MOVE);
                piece.addPossibleMove(movement);
            }
        } while (continueEvaluation);
    }
}
