package api.chess.gameplay.rules;

import api.chess.player.Player;
import api.config.MovementRuleConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Turn {
    private final transient static Logger LOG = Logger.getLogger(Turn.class.getName());

    private PieceConfig.Color playerColor;
    private Movement movement;
    private boolean checked;
    private boolean checkmated;
    private Date startTime;
    private Date endTime;
    private String duration;


    public Turn(PieceConfig.Color playerColor, Movement movement, boolean checked, boolean checkmated, Date startTime, Date endTime) {
        this.playerColor = playerColor;
        this.movement = movement;
        this.checked = checked;
        this.checkmated = checkmated;
        this.startTime = startTime;
        this.endTime = endTime;
        long millis = endTime.getTime() - startTime.getTime();
        duration = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public PieceConfig.Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PieceConfig.Color playerColor) {
        this.playerColor = playerColor;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isCheckmated() {
        return checkmated;
    }

    public void setCheckmated(boolean checkmated) {
        this.checkmated = checkmated;
    }

    public Date getEndTime() {
        return endTime;
    }
}
