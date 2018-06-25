package api.chess.gameplay.game;

import api.config.GameConfig;

import java.util.logging.Logger;

public class Game {
    private final static Logger LOG = Logger.getLogger(Game.class.getName());

    public static GameConfig.PieceSet pieceSet = GameConfig.PieceSet.DEFAULT;

}
