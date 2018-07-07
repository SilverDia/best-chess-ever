package api.config;

import api.chess.equipment.board.Coordinates;

import java.util.logging.Logger;

public class BoardConfig {
	private final static Logger LOG = Logger.getLogger(BoardConfig.class.getName());

	public static String toSquareId(int x, int y) {
		String rankId = String.valueOf((char) (x + 65));
		String fileId = String.valueOf((char) (y + 49));
		return rankId + fileId;
	}

	public static String toSquareId(Coordinates coordinates) {
		return toSquareId(coordinates.getX(), coordinates.getY());
	}

	public static Coordinates toCoordinates(String suqareId) {
		int x = suqareId.charAt(0) - 65;
		int y = suqareId.charAt(1) - 49;
		return new Coordinates(x, y);
	}

	/**
	 *
	 * @param color
	 *            Color of the player, used to parse coordinates if necessary
	 * @param coordinates
	 *            Use coordinates of WHITE -> y-coordinate will be adapted for BLACK
	 * @return Id of the square; E.g. A1, G6, ...
	 */
	public static String toInitSquareId(PieceConfig.Color color, Coordinates coordinates) {
		if (color.equals(PieceConfig.Color.BLACK)) {
			if (coordinates.getY() == 0) {
				coordinates.setY(7);
			} else if (coordinates.getY() == 1) {
				coordinates.setY(6);
			}
		}
		return toSquareId(coordinates.getX(), coordinates.getY());
	}
}
