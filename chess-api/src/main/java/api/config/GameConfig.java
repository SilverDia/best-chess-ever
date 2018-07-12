package api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import api.chess.gameplay.rules.Movement;

public class GameConfig {
    private final static Logger LOG = Logger.getLogger(GameConfig.class.getName());

    public enum PieceImageSet {
        DEFAULT, SIMPSONS, SOUTH_PARK, PEANUTS, POKEMON
    }

    public static final String RESOURCE_PATH = "/ChessGame/project/resources/";
    public static final String RESOURCE_PATH_IMAGES = RESOURCE_PATH + "images/";
    public static final String RESOURCE_PATH_IMAGES_PIECESETS = RESOURCE_PATH_IMAGES + "piece-sets/";
    
	public static List<Movement> intersectLists(List<Movement> l1, List<Movement> l2, Predicate<Movement> l2conditions) {
		List<Movement> intersection = new ArrayList<>();
		intersection.addAll(l1.stream().filter(move -> testConditions(move.getMoveToSquareId(), l2, l2conditions)).collect(Collectors.toList()));
		return intersection;
	}
	
	private static boolean testConditions(String squareId, List<Movement> list, Predicate<Movement> conditions) {
		return getMovesFromSquare(squareId, list).stream().anyMatch(conditions);
	}
	
	private static List<Movement> getMovesFromSquare(String squareId, List<Movement> list) {
		return list.stream().filter(move -> squareId.equals(move.getMoveToSquareId())).collect(Collectors.toList());
	}
}
