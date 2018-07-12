package api.chess.gameplay.game;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Direction;
import api.chess.equipment.pieces.King;
import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.rules.Move;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Turn;
import api.chess.player.Player;
import api.config.BoardConfig;
import api.config.GameConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Game {

	public enum GameState {
		CLEAR, CHECK, CHECKMATE;
	}

	private final transient static Logger LOG = Logger.getLogger(Game.class.getName());

	public static GameConfig.PieceImageSet pieceSet = GameConfig.PieceImageSet.DEFAULT;

	public final String gameId = "game_" + new Date().getTime();

	private Board board;

	private LinkedList<Turn> turnHistory = new LinkedList<>();

	private HashMap<PieceConfig.Color, Player> player = new HashMap<>();
	private PieceConfig.Color activePlayer;
	private PieceConfig.Color inactivePlayer;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void init(String namePlayerWhite, String namePlayerBlack) {
		activePlayer = PieceConfig.Color.WHITE;
		inactivePlayer = PieceConfig.Color.BLACK;

		player.put(activePlayer, new Player());
		player.put(inactivePlayer, new Player());

		player.get(activePlayer).initPlayer(namePlayerWhite, PieceConfig.Color.WHITE);
		player.get(inactivePlayer).initPlayer(namePlayerBlack, PieceConfig.Color.BLACK);

		// dummy to save game start time and to handle first turn
		turnHistory.add(new Turn(null, null, false, false, new Date(), new Date()));

		initBoard();
		evaluatePossibleMoves();
	}

	public void executeMove(String pieceId, String squareId) {
		Movement movement = player.get(activePlayer).getPieceSet().getPiece(pieceId).getMoveWithDestination(squareId);
		player.get(activePlayer).movePiece(pieceId, squareId);
		if (movement != null) {
			board.movePiece(movement);
			handleSpecialMove(movement);
			finishTurn(new Turn(activePlayer, movement, false, false, turnHistory.getLast().getEndTime(), new Date()));
		}
	}

	public void handleSpecialMove(Movement movement) {
		if (movement.getRules().contains(Move.CASTELING)) {
			boolean right = movement.getDirection().x > 0;
			String moveFromId = "" + movement.getMoveFromSquareId().charAt(0) + (right ? "8" : "1");
			String moveToId = "" + movement.getMoveFromSquareId().charAt(0) + (right ? "6" : "4");
			board.movePiece(new Movement(moveFromId, moveToId, null, Move.CASTELING, null));
		} else if (movement.getRules().contains(Move.EN_PASSANT)) {
			Piece piece = board.getSquare(movement.getMoveToSquareId()).getPiece();
			Direction dir = piece.getColor().equals(PieceConfig.Color.WHITE) ? Direction.VERT : Direction.VERT.invert();
			board.movePiece(new Movement(piece.getPositionSquareId(),
					BoardConfig.toSquareId(board.getSquare(piece.getPositionSquareId()).getCoordinates().next(dir, 1)),
					dir, Move.EN_PASSANT, null));
		}
	}

	private void initBoard() {
		board = new Board(this);
		for (Player player : player.values()) {
			for (Map.Entry<String, Piece> pieceEntry : player.getFreePieces().entrySet()) {
				board.getSquare(pieceEntry.getValue().getPositionSquareId()).setPiece(pieceEntry.getValue());
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

		King king = getKing(player.get(activePlayer));
		GameState state = king.evaluateCheck(board, inactivePlayerMoves);

		if (state.ordinal() >= GameState.CHECK.ordinal()) {
			Movement checkingMove = king.getCheckMove();
			Piece checkingPiece = board.getSquare(checkingMove.getMoveFromSquareId()).getPiece();
			List<Movement> movesToBlock = Move.MoveUtils.evaluateDirection(checkingMove.getRules().get(0), 8,
					checkingMove.getDirection(), board, checkingPiece);
			// adding fake move, so you can capture checkingPiece
			movesToBlock.add(new Movement(checkingPiece.getPositionSquareId(), checkingPiece.getPositionSquareId(),
					checkingMove.getDirection(), checkingMove.getRules().get(0), null));

			activePieces.stream().filter(piece -> piece != king).forEach(piece -> piece
					.setPossibleMoves(GameConfig.intersectLists(piece.getPossibleMoves(), movesToBlock, move -> true)));
		}

		inactivePieces.forEach(piece -> piece.setPossibleMoves(null));
		activePieces.forEach(Piece::removeInvalid);
		if (state.equals(GameState.CHECKMATE)) {
			if (!activePieces.stream().anyMatch(piece -> piece.getPossibleMoves() != null)) {
				//TODO game is done!
			}
		}
	}

	private King getKing(Player player) {
		List<Piece> pieces = player.getPieceSet().getPieces();
		return (King) pieces.stream().filter(piece -> piece instanceof King).findFirst().orElse(null);
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
