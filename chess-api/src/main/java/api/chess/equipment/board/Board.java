package api.chess.equipment.board;

import api.chess.gameplay.rules.Movement;
import api.config.BoardConfig;
import api.config.MovementRuleConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class Board {
    private final transient static Logger LOG = Logger.getLogger(Board.class.getName());

    private LinkedHashMap<Integer, LinkedHashMap<String, Square>> chessBoard = new LinkedHashMap<>();

    public Board() {
        Coordinates coordinates;
        for (int y = 0; y < 8; y++) {
            LinkedHashMap<String, Square> chessBoardRow = new LinkedHashMap<>();
            for (int x = 0; x < 8; x++) {
                coordinates = new Coordinates(x, y);
                chessBoardRow.put(BoardConfig.toSquareId(coordinates), new Square(coordinates));
            }
            chessBoard.put(y, chessBoardRow);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public MovementRuleConfig.Move movePiece(Movement movement) throws IOException {
        return movePiece(movement.getMoveFromSquareId(), movement.getMoveToSquareId());
    }

    public MovementRuleConfig.Move movePiece(String fromSquareID, String toSquareID) throws IOException {
        try {
            boolean captureMove = !getSquare(toSquareID).isVacant();
            getSquare(toSquareID).setPieceId(getSquare(fromSquareID).getPieceId());
            getSquare(fromSquareID).setPieceId("");
            return captureMove ? MovementRuleConfig.Move.CAPTURE_MOVE : MovementRuleConfig.Move.BASIC_MOVE;
        } catch (Exception e) {
            throw new IOException("Invalid squareId(s) ('" + fromSquareID + "', '" + toSquareID + "')\n", e);
        }
    }

    public Square getSquare(String squareId) {
        Coordinates coordinates = BoardConfig.toCoordinates(squareId);
        return getSquare(coordinates.x, coordinates.y);
    }

    public Square getSquare(int x, int y) {
        return chessBoard.get(y).get(BoardConfig.toSquareId(x, y));
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
