package api.config;

import java.util.logging.Logger;

public class GameConfig {
    private final static Logger LOG = Logger.getLogger(GameConfig.class.getName());

    public enum PieceSet {
        DEFAULT, SIMPSONS, SOUTH_PARK, PEANUTS, POKEMON
    }

    public static final String RESOURCE_PATH = "/ChessGame/project/resources/";
    public static final String RESOURCE_PATH_IMAGES = RESOURCE_PATH + "images/";
    public static final String RESOURCE_PATH_IMAGES_PIECESETS = RESOURCE_PATH_IMAGES + "piece-sets/";
}
