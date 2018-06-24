package api.chess.equipment.pieces;

import api.chess.equipment.Square;
import api.config.PieceConfig.*;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Piece {
    private final static Logger LOG = Logger.getLogger(Piece.class.getName());

    private final String id; // to identify each figure --- type_color_number --- example: Pawn_W_3 / Knight_B_1

    private final PieceName name;
    private final Color color;

    private final String imageUrl;


    private Square position;
    private boolean captured;

    private ArrayList<Square> freeSquares_move = new ArrayList<>();
    private ArrayList<Square> freeSquares_capture = new ArrayList<>();

    public Piece(String id, PieceName name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageUrl = buildImageUrl();

    }

    private String buildImageUrl() {

        return "";
    }
}
