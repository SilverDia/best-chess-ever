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

	private String playerName;
	private transient Movement movement;
	private transient boolean checked;
	private transient boolean checkmated;
	private transient Date startTime;
	private transient Date endTime;
	private transient String duration;
	private transient String movedPiece;
	private transient String capturedPiece;
	private transient String extraInfo = "";
	private String message;

	public Turn(String playerName, Movement movement, String movedPiece, String capturedPiece,
			boolean checked, boolean checkmated, Date startTime, Date endTime) {
		this.playerName = playerName;
		this.movement = movement;
		this.checked = checked;
		this.checkmated = checkmated;
		this.startTime = startTime;
		this.endTime = endTime;
		this.movedPiece = movedPiece;
		this.setCapturedPiece(capturedPiece);
		long millis = endTime.getTime() - startTime.getTime();
		
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		duration = (minutes != 0?minutes + " min, ":"") + (seconds - TimeUnit.MINUTES.toSeconds(minutes) + " sek");
	}

	public void setMessage() {
		if (playerName != null) { //dummy Turn
		message = playerName + " bewegt " + movedPiece + " von " + movement.getMoveFromSquareId() + " nach "
				+ movement.getMoveToSquareId() + " in " + duration;
		if (movement.getRules().contains(Move.CAPTURE_MOVE))
			message += " und schl&auml;gt " + getCapturedPiece();
		message += extraInfo;
		if (checked || checkmated)
			message += " und stellt den K&ouml;nig in " + (checkmated ? "Schachmatt" : "Schach");
		}
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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

	public String getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(String capturedPiece) {
		this.capturedPiece = capturedPiece;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
}
