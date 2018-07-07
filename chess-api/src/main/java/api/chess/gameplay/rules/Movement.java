package api.chess.gameplay.rules;

import com.google.gson.Gson;

import api.chess.equipment.board.Direction;
import api.chess.equipment.pieces.Piece;

import java.util.ArrayList;

public class Movement {

    private final String moveFromSquareId;
    private final String moveToSquareId;
    private transient final Direction direction;
    private final Piece blockedBy;
    
    private ArrayList<Move> rules = new ArrayList<>();

    public Movement(String moveFromSquareId, String moveToSquareId, Direction direction, Move move, Piece blockedBy) {
        this.moveFromSquareId = moveFromSquareId;
        this.moveToSquareId = moveToSquareId;
        this.direction = direction;
        this.blockedBy = blockedBy;
        addMovementRule(move);
    }

    @Override
    public String toString() {
        return (new Gson().toJson(this));
    }

    public Movement addMovementRule(Move move) {
        rules.add(move);
        return this;
    }

    public String getMoveToSquareId() {
        return moveToSquareId;
    }

    public String getMoveFromSquareId() {
        return moveFromSquareId;
    }

    public ArrayList<Move> getRules() {
        return rules;
    }

	public Piece getBlockedBy() {
		return blockedBy;
	}

	public Direction getDirection() {
		return direction;
	}
}
