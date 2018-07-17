package api.chess.player;

import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.PieceSet;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Turn;
import api.config.MovementRuleConfig;
import api.config.PieceConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class Player {
	private final static Logger LOG = Logger.getLogger(Player.class.getName());

	private String name;
	private PieceConfig.Color color;
	private Long turnCounter = 0L;
	private Long durationFullSecs = 0L;

	private boolean active;
	private boolean isChecked;
	private boolean canCheck;

	private PieceSet pieceSet;

	private transient HashMap<String, Piece> freePieces = new HashMap<>();
	private HashMap<String, Piece> capturedPieces = new HashMap<>();

	public void initPlayer(String name, PieceConfig.Color color) {
		this.name = name;
		this.color = color;

		pieceSet = new PieceSet();
		pieceSet.init(this);

		updatePlayer();
		active = color.equals(PieceConfig.Color.WHITE);
	}

	public void updatePlayer() {
		active = !active;
		updatePlayerPieces();
	}

	public void updatePlayerPieces() {
		for (Entry<String, Piece> entry : pieceSet.getPieces().entrySet()) {
			String id = entry.getKey();
			Piece piece = entry.getValue();

			if (piece.isCaptured()) {
				capturedPieces.put(id, piece);
				freePieces.remove(id);
			} else {
				freePieces.put(id, piece);
				capturedPieces.remove(id);
			}
		}
	}

	public Piece doPromotion(String pawnPieceId, String newPieceName) {
		Piece newPiece = pieceSet.doPromotion(pawnPieceId, newPieceName);
		freePieces.put(newPiece.getId(), newPiece);
		freePieces.remove(pawnPieceId);
		return newPiece;
	}

	public Movement movePiece(String pieceId, String moveToSqaureId) {
		return pieceSet.movePiece(pieceId, moveToSqaureId);
	}

	public void removeCapturedPiece(String pieceId) {
		movePiece(pieceId, "");
	}

	public HashMap<String, Piece> getFreePieces() {
		return freePieces;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public void setCanCheck(boolean canCheck) {
		this.canCheck = canCheck;
	}

	public boolean isCanCheck() {
		return canCheck;
	}

	public String getName() {
		return name;
	}

	public PieceConfig.Color getColor() {
		return color;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public PieceSet getPieceSet() {
		return pieceSet;
	}

	public HashMap<String, Piece> getCapturedPieces() {
		return capturedPieces;
	}

	public Long getTurnCounter() {
		return turnCounter;
	}

	public void addTurn() {
		this.turnCounter++;
	}

	public Long getDurationFullSecs() {
		return durationFullSecs;
	}

	public void addDurationSecs(Long durationSecs) {
		this.durationFullSecs += durationSecs;
	}
}
