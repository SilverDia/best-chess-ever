package api.chess.equipment;

import java.util.logging.Logger;

public class Board {
    private final static Logger LOG = Logger.getLogger(Board.class.getName());

    private Square[][] board = new Square[8][8];

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j);
            }
        }
    }

    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    public Square getSquare(String squareId) {
        int x = squareId.charAt(0) - 65;
        int y = squareId.charAt(1) - 49;
        return getSquare(x, y);
    }

    public Square getNext()
}
