package api.chess.gameplay.rules;

import api.chess.player.Player;
import api.config.MovementRuleConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Turn {
    private final transient static Logger LOG = Logger.getLogger(Turn.class.getName());

    private Player player;
    private MovementRuleConfig.Move move;
    private String pieceId;
    private String fromSquareId;
    private String toSquareId;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MovementRuleConfig.Move getMove() {
        return move;
    }

    public void setMove(MovementRuleConfig.Move move) {
        this.move = move;
    }

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public String getFromSquareId() {
        return fromSquareId;
    }

    public void setFromSquareId(String fromSquareId) {
        this.fromSquareId = fromSquareId;
    }

    public String getToSquareId() {
        return toSquareId;
    }

    public void setToSquareId(String toSquareId) {
        this.toSquareId = toSquareId;
    }
}
