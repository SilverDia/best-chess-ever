package api.chess.gameplay.rules;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Movement {

    private String moveFromSquareId;
    private String moveToSquareId;
    private ArrayList<Move> rules = new ArrayList<>();

    public Movement(String moveFromSquareId, String moveToSquareId, Move move) {
        this.moveFromSquareId = moveFromSquareId;
        this.moveToSquareId = moveToSquareId;
        addMovementRule(move);
    }

    @Override
    public String toString() {
        return (new Gson().toJson(this));
    }

    public void addMovementRule(Move move) {
        rules.add(move);
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
}
