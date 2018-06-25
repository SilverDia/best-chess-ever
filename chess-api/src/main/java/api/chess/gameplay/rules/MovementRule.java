package api.chess.gameplay.rules;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.MovementRuleConfig;

import java.util.ArrayList;

public class MovementRule {

    public static void evaluateMovement(Piece piece, Board board) {
        for (MovementRuleConfig.Move move : piece.getMoves()) {
            if (move.toString().startsWith("STRAIGHT")) {

            }
        }
    }

//    private static ArrayList<String> evaluateMovementStraight(boolean oneSquare, String startSquareId,  Board board) {
//        int maxSteps = 7;
//        if (oneSquare) { maxSteps = 1; }
//        ArrayList<String> possibleSquareIds = new ArrayList<>();
//
//        for (int i = 1; i <= maxSteps; i++) {
//
//        }
//
//
//        String squareId = startSquareId;
//        Square square;
//        // move straight to right
//        while (maxSteps > 0 && (square = board.next(startSquareId, BoardConfig.Direction.HOR)) != null) {
//            maxSteps--;
//            if (square.isVacant()) {
//
//            }
//        }
//
//    }
}
