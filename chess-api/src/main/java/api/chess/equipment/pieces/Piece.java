package api.chess.equipment.pieces;

import api.chess.equipment.board.Square;
import api.config.MovementRuleConfig;
import api.config.PieceConfig.*;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Piece {
    Logger LOG = Logger.getLogger(Piece.class.getName());

    String id; // to identify each figure --- type_color_number --- example: Pawn_W_3 / Knight_B_1

    PieceName name;
    Color color;

    String imageUrl;

    String positionSquareId;
    boolean captured = false;

    ArrayList<MovementRuleConfig.Move> moves;
    ArrayList<String> freeSquares_move = new ArrayList<>();
    ArrayList<String> freeSquares_capture = new ArrayList<>();

    public PieceName getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public String getPositionSquareId() {
        return positionSquareId;
    }

    public boolean isCaptured() {
        return captured;
    }

    public ArrayList<MovementRuleConfig.Move> getMoves() {
        return moves;
    }
}
