package api.chess.gameplay.game;

import api.chess.equipment.board.Board;
import api.chess.equipment.pieces.Piece;
import api.chess.player.Player;
import api.config.GameConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Game {
    private final transient static Logger LOG = Logger.getLogger(Game.class.getName());

    public static GameConfig.PieceImageSet pieceSet = GameConfig.PieceImageSet.DEFAULT;

    private Board board = new Board();

    private HashMap<PieceConfig.Color, Player> player = new HashMap<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void init(String namePlayerWhite, String namePlayerBlack) {
        player.put(PieceConfig.Color.WHITE, new Player());
        player.put(PieceConfig.Color.BLACK, new Player());

        player.get(PieceConfig.Color.WHITE).initPlayer(namePlayerWhite, PieceConfig.Color.WHITE);
        player.get(PieceConfig.Color.BLACK).initPlayer(namePlayerBlack, PieceConfig.Color.BLACK);

        updateBoard();
    }

    private void updateBoard() {
        board = new Board();
        for (Player player : player.values()) {
            for (Map.Entry<String, Piece> pieceEntry : player.getFreePieces().entrySet()) {
                board.getSquare(pieceEntry.getValue().getPositionSquareId()).setPieceId(pieceEntry.getKey());
            }
        }
    }

}
