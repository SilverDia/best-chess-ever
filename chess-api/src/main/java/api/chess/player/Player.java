package api.chess.player;

import api.config.PieceConfig;

import java.util.logging.Logger;

public class Player {
    private final static Logger LOG = Logger.getLogger(Player.class.getName());

    private final String name;
    private final PieceConfig.Color color;

    public Player(String name, PieceConfig.Color color) {
        this.name = name;
        this.color = color;
    }
}
