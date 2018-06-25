package api.chess.equipment.board;

import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.PieceConfig;

import java.util.logging.Logger;

public class Square {
    private final static Logger LOG = Logger.getLogger(Square.class.getName());

    private final String squareId;
    private final PieceConfig.Color color;

    private boolean vacant = true;
    private Piece piece = null;

    private final Coordinates coordinates;

    public Square(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.squareId = BoardConfig.toSquareId(coordinates);
        if ((coordinates.x + coordinates.y) % 2 == 0) {
            color = PieceConfig.Color.BLACK;
        } else {
            color = PieceConfig.Color.WHITE;
        }
    }

    public String getSquareId() {
        return squareId;
    }

    public boolean isVacant() {
        return vacant;
    }

    public Piece getPiece() {
        return piece;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        vacant = (piece == null);
    }
}
