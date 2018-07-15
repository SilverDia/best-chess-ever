package api.chess.equipment.board;

public class Direction {
	public final int x;
	public final int y;

	public static final Direction DIAG_LT = new Direction(-1, 1);
	public static final Direction VERT = new Direction(0, 1);
	public static final Direction DIAG_RT = new Direction(1, 1);
	public static final Direction HOR = new Direction(1, 0);

	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Direction rotate() {
		return new Direction(-y, x);
	}

	public Direction invert() {
		return new Direction(-x, -y);
	}

	public Direction add(Direction dir) {
		return new Direction(dir.x + x, dir.y + y);
	}

	public Coordinates apply(Coordinates coordinates, int steps) {
		return new Coordinates(coordinates.getX() + x * steps, coordinates.getY() + y * steps);
	}
}