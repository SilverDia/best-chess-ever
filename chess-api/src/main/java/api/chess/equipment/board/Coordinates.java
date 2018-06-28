package api.chess.equipment.board;

import api.config.BoardConfig;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Coordinates {
    private final transient static Logger LOG = Logger.getLogger(Coordinates.class.getName());

    int x;
    int y;

    boolean valid;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
        valid = validate(x, y);

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean hasNext(BoardConfig.Direction direction, int steps) {
        return (next(direction, steps) == null);
    }

    public Coordinates next(BoardConfig.Direction direction, int steps) {
        Coordinates nextCoordinates = null;
        switch (direction) {
            case DIAG_LT: {
                nextCoordinates = new Coordinates(x-steps, y+steps);
                break;
            }
            case VERT: {
                nextCoordinates = new Coordinates(x, y+steps);
                break;
            }
            case DIAG_RT: {
                nextCoordinates = new Coordinates(x+steps, y+steps);
                break;
            }
            case HOR: {
                nextCoordinates = new Coordinates(x+steps, y);
                break;
            }
        }
        return nextCoordinates.valid ? nextCoordinates : null;
    }

    public boolean hasPrev(BoardConfig.Direction direction, int steps) {
        return (prev(direction, steps) == null);
    }

    public Coordinates prev(BoardConfig.Direction direction, int steps) {
        Coordinates prevCoordinates = null;
        switch (direction) {
            case DIAG_LT: {
                prevCoordinates = new Coordinates(x-steps, y-steps);
                break;
            }
            case VERT: {
                prevCoordinates = new Coordinates(x, y-steps);
                break;
            }
            case DIAG_RT: {
                prevCoordinates = new Coordinates(x+steps, y-steps);
                break;
            }
            case HOR: {
                prevCoordinates = new Coordinates(x-steps, y);
                break;
            }
        }
        return prevCoordinates.valid ? prevCoordinates : null;
    }

    private boolean validate(int x, int y) {
        return (x >= 0 && x < 8 && y >= 0 && y < 8);
    }
}
