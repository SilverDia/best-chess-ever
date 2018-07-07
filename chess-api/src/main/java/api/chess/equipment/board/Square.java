package api.chess.equipment.board;

import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.PieceConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Square {
    private final transient static Logger LOG = Logger.getLogger(Square.class.getName());

    private final String squareId;
    private final PieceConfig.Color color;

    private boolean vacant = true;
    private Piece piece = null;
    private String pieceId = "";

    private final Coordinates coordinates;

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
        vacant = (piece == null);
    }

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
        vacant = (pieceId.equals(""));
    }
}
