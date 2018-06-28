package api.chess.player;

import api.chess.equipment.pieces.Piece;
import api.chess.equipment.pieces.PieceSet;
import api.chess.gameplay.rules.Turn;
import api.config.PieceConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Player {
    private final static Logger LOG = Logger.getLogger(Player.class.getName());

    private String name;
    private PieceConfig.Color color;

    private PieceSet pieceSet;

    private HashMap<String, Piece> freePieces = new HashMap<>();
    private HashMap<String, Piece> capturedPieces = new HashMap<>();

    private LinkedList<Turn> lastTurns = new LinkedList<>();

    public void initPlayer(String name, PieceConfig.Color color) {
        this.name = name;
        this.color = color;

        pieceSet = new PieceSet();
        pieceSet.init(color);

        updatePlayer();
    }

    public void updatePlayer() {
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

    public void movePiece(String pieceId, String moveToSqaureId) {
        pieceSet.movePiece(pieceId, moveToSqaureId);
        updatePlayer();
    }

    public void removeCapturedPiece(String pieceId) {
        movePiece(pieceId, "");
    }

    public HashMap<String, Piece> getFreePieces() {
        return freePieces;
    }
}
