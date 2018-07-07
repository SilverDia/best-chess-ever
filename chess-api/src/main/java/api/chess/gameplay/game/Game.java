package api.chess.gameplay.game;

import api.chess.equipment.board.Board;
import api.chess.equipment.pieces.Piece;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Turn;
import api.chess.player.Player;
import api.config.GameConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

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
    }

    public void executeMove(String pieceId, String squareId) throws IOException{
        try {
            Movement movement = player.get(activePlayer).movePiece(pieceId, squareId);
            if (movement != null) {
                board.movePiece(movement);
                finishTurn(new Turn(activePlayer, movement, false, false, turnHistory.getLast().getEndTime(), new Date()));
            }
        } catch (Exception e) {
            throw new IOException("Failed to execute move:\n", e);
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
        inactivePlayer = inactivePlayer.equals(PieceConfig.Color.WHITE) ? PieceConfig.Color.BLACK : PieceConfig.Color.WHITE;
        for (Player p : player.values()) {
            p.updatePlayer();
        }
        // FIXME ▼
        evaluatePossibleMoves();

        turn.setChecked(player.get(activePlayer).isChecked());
        turnHistory.add(turn);
    }

    private void evaluatePossibleMoves() {
        // TODO Ich schätz mal hier wärs am besten...
        // TODO Zuerst den inactive Player...
        // TODO Danach den active Player wegen schach und so...
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
