package api.chess.gameplay.game;

import api.chess.equipment.board.Board;
import api.chess.equipment.pieces.King;
import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Turn;
import api.chess.player.Player;
import api.config.GameConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Game {
	private final transient static Logger LOG = Logger.getLogger(Game.class.getName());

	public static GameConfig.PieceImageSet pieceSet = GameConfig.PieceImageSet.DEFAULT;

	public final String gameId = "game_" + new Date().getTime();

	private Board board = new Board();

	private LinkedList<Turn> turnHistory = new LinkedList<>();

	private HashMap<PieceConfig.Color, Player> player = new HashMap<>();
	private PieceConfig.Color activePlayer;
	private PieceConfig.Color inactivePlayer;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void init(String namePlayerWhite, String namePlayerBlack) {
		player.put(PieceConfig.Color.WHITE, new Player());
		player.put(PieceConfig.Color.BLACK, new Player());

		player.get(PieceConfig.Color.WHITE).initPlayer(namePlayerWhite, PieceConfig.Color.WHITE);
		player.get(PieceConfig.Color.BLACK).initPlayer(namePlayerBlack, PieceConfig.Color.BLACK);

		activePlayer = PieceConfig.Color.WHITE;
		inactivePlayer = PieceConfig.Color.BLACK;
		turnHistory.add(new Turn(null, null, false, false, new Date(), new Date())); // dummy to save game start time and to handle first turn
		initBoard();
		evaluatePossibleMoves();
	}

	public boolean executeMove(String pieceId, String squareId) {
		try {
			Movement movement = player.get(activePlayer).movePiece(pieceId, squareId);
			if (movement != null) {
				board.movePiece(movement);
				finishTurn(
						new Turn(activePlayer, movement, false, false, turnHistory.getLast().getEndTime(), new Date()));
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private void initBoard() {
		board = new Board();
		for (Player player : player.values()) {
			for (Map.Entry<String, Piece> pieceEntry : player.getFreePieces().entrySet()) {
				board.getSquare(pieceEntry.getValue().getPositionSquareId()).setPieceId(pieceEntry.getKey());
			}
		}
	}

	private void finishTurn(Turn turn) {
		activePlayer = activePlayer.equals(PieceConfig.Color.WHITE) ? PieceConfig.Color.BLACK : PieceConfig.Color.WHITE;
		inactivePlayer = inactivePlayer.equals(PieceConfig.Color.WHITE) ? PieceConfig.Color.BLACK
				: PieceConfig.Color.WHITE;
		for (Player p : player.values()) {
			p.updatePlayer();
		}
		evaluatePossibleMoves();

		turn.setChecked(player.get(activePlayer).isChecked());
		turnHistory.add(turn);
	}

	private void evaluatePossibleMoves() {
		List<Piece> inactivePieces = player.get(inactivePlayer).getPieceSet().getPieces();
		List<Movement> inactivePlayerMoves = new ArrayList<>();
		inactivePieces.forEach(piece -> inactivePlayerMoves.addAll(piece.evaluate(board)));

		List<Piece> activePieces = player.get(activePlayer).getPieceSet().getPieces();
		List<Movement> activePlayerMoves = new ArrayList<>();
		activePieces.forEach(piece -> activePlayerMoves.addAll(piece.evaluate(board)));

		GameState state = getKing(player.get(activePlayer)).evaluateCheck(board, inactivePlayerMoves);
	}

	private King getKing(Player player) {
		List<Piece> pieces = player.getPieceSet().getPieces();
		return (King) pieces.stream().filter(piece -> piece instanceof King).collect(Collectors.toList()).get(0);
	}

	public String getGameId() {
		return gameId;
	}

	public Board getBoard() {
		return board;
	}

	public HashMap<PieceConfig.Color, Player> getPlayer() {
		return player;
	}

	public LinkedList<Turn> getTurnHistory() {
		return turnHistory;
	}

	public PieceConfig.Color getActivePlayer() {
		return activePlayer;
	}
}
