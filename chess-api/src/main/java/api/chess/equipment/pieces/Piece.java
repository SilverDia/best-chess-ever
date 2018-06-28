package api.chess.equipment.pieces;

import api.chess.equipment.board.Coordinates;
import api.chess.equipment.board.Square;
import api.chess.gameplay.rules.MovementRule;
import api.config.BoardConfig;
import api.config.MovementRuleConfig;
import api.config.PieceConfig.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.logging.Logger;

public abstract class Piece {
    transient Logger LOG = Logger.getLogger(Piece.class.getName());

    String id; // to identify each figure --- type_color_number --- example: Pawn_W_3 / Knight_B_1

    PieceName name;
    Color color;

    String imageUrl;

    String positionSquareId;
    boolean captured = false;

    ArrayList<MovementRuleConfig.Move> moves;
    ArrayList<String> freeSquares_move = new ArrayList<>();
    ArrayList<String> freeSquares_capture = new ArrayList<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void init(int id, Color color) {
        this.color = color;
        this.id = name.toString() + "_" + color.toString() + "_" + String.valueOf(id);

        moves = MovementRuleConfig.getMoves(name);

    }

    public String initPosition(Coordinates coordinates, int id, int scale) {
        coordinates.setX(coordinates.getX() + (id*scale));
        return BoardConfig.toInitSquareId(color, coordinates);
    }

    public String getId() { return id; }

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

    public void setPositionSquareId(String positionSquareId) {
        this.positionSquareId = positionSquareId;
        captured = positionSquareId.equals("");
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
        if (captured) {
            positionSquareId = "";
        }
    }
}
