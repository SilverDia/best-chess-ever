package api.chess.equipment.board;

import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.game.Game;
import api.chess.gameplay.rules.Move;
import api.chess.gameplay.rules.Movement;
import api.config.BoardConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class Board {
	private final transient static Logger LOG = Logger.getLogger(Board.class.getName());

	private LinkedHashMap<Integer, LinkedHashMap<String, Square>> chessBoard = new LinkedHashMap<>();
	private transient final Game game;

	public Board(Game game) {
		this.game = game;
		Coordinates coordinates;
		for (int y = 0; y < 8; y++) {
			LinkedHashMap<String, Square> chessBoardRow = new LinkedHashMap<>();
			for (int x = 0; x < 8; x++) {
				coordinates = new Coordinates(x, y);
				chessBoardRow.put(BoardConfig.toSquareId(coordinates), new Square(coordinates));
			}
			chessBoard.put(y, chessBoardRow);
		}
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void movePiece(Movement movement) {
		String fromSquareID = movement.getMoveFromSquareId();
		String toSquareID = movement.getMoveToSquareId();

		Piece piece = getSquare(fromSquareID).getPiece();
		if (movement.getRules().contains(Move.CAPTURE_MOVE) && getSquare(toSquareID).getPiece() != null)
			getSquare(toSquareID).getPiece().setCaptured(true);
		piece.setMoved(true);

		getSquare(toSquareID).setPiece(piece);
		getSquare(fromSquareID).setPiece(null);
	}

	public Square getSquare(String squareId) {
		Coordinates coordinates = BoardConfig.toCoordinates(squareId);
		return getSquare(coordinates.getX(), coordinates.getY());
	}

	public Square getSquare(int x, int y) {
		return chessBoard.get(y).get(BoardConfig.toSquareId(x, y));
	}
	
	public Game getGame() {
		return game;
	}
}
