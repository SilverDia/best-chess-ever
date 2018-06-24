package api.chess.gameplay;

import api.chess.equipment.Board;
import api.chess.equipment.Square;
import api.chess.equipment.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DefaultRule implements MovementRule{

    private final static Logger LOG = Logger.getLogger(DefaultRule.class.getName());

    private final Move move;
    private final boolean stepsLimited;

    public DefaultRule(Move move, boolean stepsLimited) {
        this.move = move;
        this.stepsLimited = stepsLimited;
    }

    public static void evaluateRule(DefaultRule defaultRule, Square position, Board board) {
        int steps = 7;
        if (defaultRule.stepsLimited) {
            steps = 1;
        }
        switch (defaultRule.move) {
            case DIAGONAL:
        }
    }

    private ArrayList
}
