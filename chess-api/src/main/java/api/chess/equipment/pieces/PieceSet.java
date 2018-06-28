package api.chess.equipment.pieces;

import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import static api.config.PieceConfig.PieceName.*;
import static java.lang.String.valueOf;

public class PieceSet {
    private final transient static Logger LOG = Logger.getLogger(PieceSet.class.getName());

    private transient ArrayList<Piece> pieces = new ArrayList<>();

    private King king;
    private Queen queen;

    private HashMap<String, Bishop> bishops = new HashMap<>();
    private HashMap<String, Knight> knights = new HashMap<>();
    private HashMap<String, Rook>  rooks = new HashMap<>();

    private HashMap<String, Pawn>  pawns = new HashMap<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void init(PieceConfig.Color color) {
        king = new King();
        king.init(0, color);
        queen = new Queen();
        queen.init(0, color);

        for (int i = 0; i < 2; i++) {
            Bishop bishop = new Bishop();
            bishop.init(i, color);
            bishops.put(bishop.id, bishop);
            Rook rook = new Rook();
            rook.init(i, color);
            rooks.put(rook.id, rook);
            Knight knight= new Knight();
            knight.init(i, color);
            knights.put(knight.id, knight);
        }
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn();
            pawn.init(i, color);
            pawns.put(pawn.id, pawn);
        }

        pieces.add(king);
        pieces.add(queen);
        pieces.addAll(bishops.values());
        pieces.addAll(rooks.values());
        pieces.addAll(knights.values());
        pieces.addAll(pawns.values());
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void movePiece(String pieceId, String moveToSqaureId) {
        String pieceName = getPieceName(pieceId);

        if (pieceName.equals(KING)) {
            king.setPositionSquareId(moveToSqaureId);
        } else if (pieceName.equals(QUEEN)) {
            queen.setPositionSquareId(moveToSqaureId);
        } else if (pieceName.equals(BISHOP)) {
            bishops.get(pieceId).setPositionSquareId(moveToSqaureId);
        } else if (pieceName.equals(KNIGHT)) {
            knights.get(pieceId).setPositionSquareId(moveToSqaureId);
        } else if (pieceName.equals(ROOK)) {
            rooks.get(pieceId).setPositionSquareId(moveToSqaureId);
        } else if (pieceName.equals(PAWN)) {
            pawns.get(pieceId).setPositionSquareId(moveToSqaureId);
        }
    }

    public void removeCapturedPiece(String pieceId) {
        movePiece(pieceId, "");
    }

    private String getPieceName(String pieceId) {
        return pieceId.split("_")[0];
    }


}
