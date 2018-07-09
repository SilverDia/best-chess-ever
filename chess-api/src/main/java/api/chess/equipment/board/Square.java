package api.chess.equipment.board;

import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Square {
    private final transient static Logger LOG = Logger.getLogger(Square.class.getName());

    private final String squareId;
    private transient final PieceConfig.Color color;

    private transient boolean vacant = true;
    private transient Piece piece = null;
    private String pieceID = "";

    private transient final Coordinates coordinates;

    public Square(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.squareId = BoardConfig.toSquareId(coordinates);
        if ((coordinates.getX() + coordinates.getY()) % 2 == 0) {
            color = PieceConfig.Color.BLACK;
        } else {
            color = PieceConfig.Color.WHITE;
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getSquareId() {
        return squareId;
    }

    public boolean isVacant() {
        return vacant;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        pieceID = piece.getId();
        vacant = (piece == null);
    }
}
