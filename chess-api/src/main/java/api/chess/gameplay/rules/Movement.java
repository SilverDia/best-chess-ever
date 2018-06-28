package api.chess.gameplay.rules;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Square;
import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.MovementRuleConfig;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Movement {

    private String moveToSquareId;
    private ArrayList<MovementRuleConfig> rules = new ArrayList<>();

    @Override
    public String toString() {
        return (new Gson().toJson(this));
    }

    public String getMoveToSquareId() {
        return moveToSquareId;
    }

    public ArrayList<MovementRuleConfig> getRules() {
        return rules;
    }
}
