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
import java.util.logging.Logger;

public class Player {
    private final static Logger LOG = Logger.getLogger(Player.class.getName());

    private String name;
    private PieceConfig.Color color;
    private boolean active;
    private boolean isChecked;
    private boolean canCheck;

    private PieceSet pieceSet;

    private transient HashMap<String, Piece> freePieces = new HashMap<>();
    private transient HashMap<String, Piece> capturedPieces = new HashMap<>();

    public void initPlayer(String name, PieceConfig.Color color) {
        this.name = name;
        this.color = color;

        pieceSet = new PieceSet();
        pieceSet.init(color);

        updatePlayer();
        active = color.equals(PieceConfig.Color.WHITE);
    }

    public void updatePlayer() {
        active = !active;
        for (Piece piece : pieceSet.getPieces()) {
            String id = piece.getId();
            if (piece.isCaptured()) {
                capturedPieces.put(id, piece);
                freePieces.remove(id);
            } else {
                freePieces.put(id, piece);
                capturedPieces.remove(id);
            }
        }
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
}
