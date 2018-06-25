package api.chess.equipment.board;

import api.chess.equipment.pieces.Pawn;
import api.chess.equipment.pieces.Piece;
import api.config.BoardConfig;
import api.config.PieceConfig;

import java.util.HashMap;
import java.util.logging.Logger;

public class Board {
    private final static Logger LOG = Logger.getLogger(Board.class.getName());

    private HashMap<String, Square> board = new HashMap<>();

    public Board() {
        Coordinates coordinates;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                coordinates = new Coordinates(x, y);
                board.put(BoardConfig.toSquareId(coordinates), new Square(coordinates));
            }
        }
    }

    public void init() {
        for (int x = 0; x < 8; x++) {
            getSquare(x, 1).setPiece(new Pawn(x, PieceConfig.Color.WHITE));
            getSquare(x, 6).setPiece(new Pawn(x, PieceConfig.Color.BLACK));
        }
    }

    public Square getSquare(String squareId) {
        return board.getOrDefault(squareId, null);
    }

    public Square getSquare(int x, int y) {
        return board.get(BoardConfig.toSquareId(x, y));
    }

//    public boolean hasNext(String fromSquareId, BoardConfig.Direction direction) {
//        return (board.get(fromSquareId).getCoordinates().hasNext(direction));
//    }
//
//    public Square next(String fromSquareId, BoardConfig.Direction direction) {
//        return board.get(BoardConfig.toSquareId(
//                board.get(fromSquareId).getCoordinates().next(direction))
//        );
//    }
//
//    public boolean hasPrev(String fromSquareId, BoardConfig.Direction direction) {
//        return (board.get(fromSquareId).getCoordinates().hasPrev(direction));
//    }
//
//    public Square prev(String fromSquareId, BoardConfig.Direction direction) {
//        return board.get(BoardConfig.toSquareId(
//                board.get(fromSquareId).getCoordinates().prev(direction))
//        );
//    }
}
