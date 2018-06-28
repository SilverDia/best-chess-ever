package api.chess.equipment.board;

import api.config.BoardConfig;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class Board {
    private final transient static Logger LOG = Logger.getLogger(Board.class.getName());

    private LinkedHashMap<String, Square> chessBoard = new LinkedHashMap<>();

    public Board() {
        Coordinates coordinates;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                coordinates = new Coordinates(x, y);
                chessBoard.put(BoardConfig.toSquareId(coordinates), new Square(coordinates));
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public Square getSquare(String squareId) {
        return chessBoard.getOrDefault(squareId, null);
    }

    public Square getSquare(int x, int y) {
        return chessBoard.get(BoardConfig.toSquareId(x, y));
    }

//    public boolean hasNext(String fromSquareId, BoardConfig.Direction direction) {
//        return (chessBoard.get(fromSquareId).getCoordinates().hasNext(direction));
//    }
//
//    public Square next(String fromSquareId, BoardConfig.Direction direction) {
//        return chessBoard.get(BoardConfig.toSquareId(
//                chessBoard.get(fromSquareId).getCoordinates().next(direction))
//        );
//    }
//
//    public boolean hasPrev(String fromSquareId, BoardConfig.Direction direction) {
//        return (chessBoard.get(fromSquareId).getCoordinates().hasPrev(direction));
//    }
//
//    public Square prev(String fromSquareId, BoardConfig.Direction direction) {
//        return chessBoard.get(BoardConfig.toSquareId(
//                chessBoard.get(fromSquareId).getCoordinates().prev(direction))
//        );
//    }
}
