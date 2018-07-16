package api.chess.equipment.pieces;

import api.chess.gameplay.rules.Movement;
import api.chess.player.Player;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.logging.Logger;

import static java.lang.String.valueOf;

public class PieceSet {
	private final transient static Logger LOG = Logger.getLogger(PieceSet.class.getName());

	private HashMap<String, Piece> pieces = new HashMap<>();

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void init(Player player) {
		PieceConfig.Color color = player.getColor();

		Piece king = new King().init(0, color);
		pieces.put(king.id, king);

		Piece queen = new Queen().init(0, color);
		pieces.put(queen.id, queen);

		for (int i = 0; i < 2; i++) {
			Piece bishop = new Bishop().init(i, color);
			pieces.put(bishop.id, bishop);

			Piece rook = new Rook().init(i, color);
			pieces.put(rook.id, rook);

			Piece knight = new Knight().init(i, color);
			pieces.put(knight.id, knight);
		}
		for (int i = 0; i < 8; i++) {
			Piece pawn = new Pawn().init(i, color);
			pieces.put(pawn.id, pawn);
		}
	}

	public HashMap<String, Piece> getPieces() {
		return pieces;
	}

	public Piece getPiece(String pieceId) {
		return pieces.get(pieceId);
	}

	public Movement movePiece(String pieceId, String moveToSqaureId) {
		return pieces.get(pieceId).move(moveToSqaureId);
	}

	public void removeCapturedPiece(String pieceId) {
		movePiece(pieceId, "");
	}

	public void doPromotion(String pawnPieceId, String newPieceName) {
		PieceConfig.Color color = getPiece(pawnPieceId).getColor();
		String position = getPiece(pawnPieceId).getPositionSquareId();
		String pieceName = newPieceName.toUpperCase();
		Piece newPiece = getPiece(pawnPieceId);
		if (pieceName.equals(PieceConfig.PieceName.QUEEN.toString())) {
			newPiece = new Queen().init(createId(PieceConfig.PieceName.QUEEN, color), color);
		} else if (pieceName.equals(PieceConfig.PieceName.KNIGHT.toString())) {
			newPiece = new Knight().init(createId(PieceConfig.PieceName.KNIGHT, color), color);
		} else if (pieceName.equals(PieceConfig.PieceName.BISHOP.toString())) {
			newPiece = new Bishop().init(createId(PieceConfig.PieceName.BISHOP, color), color);
		} else if (pieceName.equals(PieceConfig.PieceName.ROOK.toString())) {
			newPiece = new Rook().init(createId(PieceConfig.PieceName.ROOK, color), color);
		}
		newPiece.setPositionSquareId(position);
		pieces.get(pawnPieceId).positionSquareId = "";
		pieces.get(pawnPieceId).captured = true;
		pieces.put(newPiece.id, newPiece);
	}

	private int createId(PieceConfig.PieceName pieceName, PieceConfig.Color color) {
		int i = 0;
		String id = pieceName.toString() + "_" + color.toString() + "_";
		while (getPiece(id + i) != null) {
			i++;
		}
		return i;
	}

	private String getPieceName(String pieceId) {
		return pieceId.split("_")[0];
	}
}
