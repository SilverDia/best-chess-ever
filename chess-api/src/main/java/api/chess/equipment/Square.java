package api.chess.equipment;

import api.chess.equipment.pieces.Piece;

import java.util.logging.Logger;

public class Square {
    private final static Logger LOG = Logger.getLogger(Square.class.getName());

    private final String id;
    private final String fileId;
    private final String rankId;

    private final String color;

    private boolean vacant = true;
    private Piece piece = null;

    private final int position_X;
    private final int position_Y;

    public Square(int position_X, int position_Y) {
        this.position_X = position_X;
        this.position_Y = position_Y;

        rankId = String.valueOf(Character.forDigit(position_X, 65));
        fileId = String.valueOf(Character.forDigit(position_Y, 49));

        id = rankId + fileId;

        if ((position_X + position_Y) % 2 == 0) {
            color = "black";
        } else {
            color = "white";
        }
    }
}
