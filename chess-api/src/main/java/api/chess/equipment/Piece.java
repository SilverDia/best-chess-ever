package api.chess.equipment;

import java.util.logging.Logger;

public class Piece {
    private final static Logger LOG = Logger.getLogger(Piece.class.getName());

    private final String id;

    private final String type;
    private final String color;

    // TODO maybe???
    private String position;

    // TODO change to Rule class
    private final String movementRule;


    public Piece(String id, String type, String color, String position, String movementRule) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.position = position;
        this.movementRule = movementRule;
    }
}
