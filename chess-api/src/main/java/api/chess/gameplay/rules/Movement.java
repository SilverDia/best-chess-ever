package api.chess.gameplay.rules;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.MovementRuleConfig;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Movement {

    private String moveFromSquareId;
    private String moveToSquareId;
    private ArrayList<MovementRuleConfig.Move> rules = new ArrayList<>();

    public Movement(String moveFromSquareId, String moveToSquareId, MovementRuleConfig.Move move) {
        this.moveFromSquareId = moveFromSquareId;
        this.moveToSquareId = moveToSquareId;
        addMovementRule(move);
    }

    @Override
    public String toString() {
        return (new Gson().toJson(this));
    }

    public void addMovementRule(MovementRuleConfig.Move move) {
        rules.add(move);
    }

    public String getMoveToSquareId() {
        return moveToSquareId;
    }

    public String getMoveFromSquareId() {
        return moveFromSquareId;
    }

    public ArrayList<MovementRuleConfig.Move> getRules() {
        return rules;
    }
}
