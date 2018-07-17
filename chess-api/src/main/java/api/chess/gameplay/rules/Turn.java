package api.chess.gameplay.rules;

import api.chess.player.Player;
import api.config.MovementRuleConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Turn {
	private final transient static Logger LOG = Logger.getLogger(Turn.class.getName());

	private String playerName;
	private Movement movement;
	private boolean checked;
	private boolean checkmated;
	private transient Date startTime;
	private transient Date endTime;
	private transient String duration;
	private transient String movedPiece;
	private transient String capturedPiece;
	private transient String timeStamp;
	private long seconds;
	private transient String extraInfo = "";
	private String message;

	public Turn(String playerName, Movement movement, String movedPiece, String capturedPiece, boolean checked,
			boolean checkmated, Date startTime, Date endTime) {
		this.playerName = playerName;
		this.movement = movement;
		this.checked = checked;
		this.checkmated = checkmated;
		this.startTime = startTime;
		this.endTime = endTime;
		this.movedPiece = movedPiece;
		this.setCapturedPiece(capturedPiece);

		Calendar cal = Calendar.getInstance();
		timeStamp = format(String.format("-%02d:%02d:%02d-: ", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND)), "black");

		long millis = endTime.getTime() - startTime.getTime();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		duration = (minutes != 0 ? minutes + " min, " : "") + (seconds - TimeUnit.MINUTES.toSeconds(minutes) + " sek");
	}

	public void setMessage() {
		if (playerName != null) { // not a dummy Turn
			message = timeStamp + format(playerName, playerName.endsWith("(Weiss)") ? "white" : "black") + " bewegt "
					+ format(movedPiece, "black") + " von " + format(movement.getMoveFromSquareId(), "aqua") + " nach "
					+ format(movement.getMoveToSquareId(), (checked || checkmated) ? "lightcoral" : "aqua") + " in "
					+ duration;
			if (movement.getRules().contains(Move.CAPTURE_MOVE))
				message += " und schl&auml;gt " + format(getCapturedPiece(), "black");
			message += extraInfo;
			if (checked || checkmated)
				message += " und stellt den " + format("K&ouml;nig", "black")
						+ (checkmated ? format(" Schachmatt", "red") : " in " + format("Schach", "red"));
		}
	}

	private String format(String string, String color) {
		return "<b style='color: " + color + ";'>" + string + "</b>";
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

	public Long getDurationSecs() {
		return seconds;
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
