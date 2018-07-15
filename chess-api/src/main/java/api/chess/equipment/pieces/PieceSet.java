package api.chess.equipment.pieces;

import api.chess.equipment.board.Board;
import api.chess.gameplay.rules.Movement;
import api.chess.player.Player;
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

	private String getPieceName(String pieceId) {
		return pieceId.split("_")[0];
	}

}
