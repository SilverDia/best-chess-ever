package api.chess.equipment.board;

import com.google.gson.Gson;

import java.util.logging.Logger;

public class Coordinates {
	private final transient static Logger LOG = Logger.getLogger(Coordinates.class.getName());

	private int x;
	private int y;

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

	public boolean hasNext(Direction direction, int steps) {
		return (next(direction, steps).valid);
	}

	public Coordinates next(Direction direction, int steps) {
		return direction.apply(this, steps);
	}

	public boolean hasPrev(Direction direction, int steps) {
		return (prev(direction, steps).valid);
	}

	public Coordinates prev(Direction direction, int steps) {
		return direction.invert().apply(this, steps);
	}

	private boolean validate(int x, int y) {
		return (x >= 0 && x < 8 && y >= 0 && y < 8);
	}
}
