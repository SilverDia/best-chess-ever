package api.chess.equipment.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.gameplay.rules.Move;
import api.chess.gameplay.rules.Movement;
import api.config.BoardConfig;
import api.config.GameConfig;
import api.config.MovementRuleConfig;
import api.config.PieceConfig;
import api.config.PieceConfig.Color;
import api.config.PieceConfig.PieceName;

public abstract class Piece {
	transient Logger LOG = Logger.getLogger(Piece.class.getName());

	String id; // to identify each figure --- type_color_number --- example: Pawn_W_3 /
				// Knight_B_1

	PieceName name;
	Color color;

	String imageUrl;

	String positionSquareId;
	boolean captured = false;
	boolean moved = false;

	List<Move> moves;

	List<Movement> possibleMoves = new ArrayList<>();

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public void init(int id, Color color) {
		this.color = color;
		this.id = name.toString() + "_" + color.toString() + "_" + String.valueOf(id);

		moves = MovementRuleConfig.getMoves(name);
		imageUrl = PieceConfig.buildImageUrl(this, GameConfig.PieceImageSet.DEFAULT);
	}

	public String initPosition(Coordinates coordinates, int id, int scale) {
		coordinates.setX(coordinates.getX() + (id * scale));
		return BoardConfig.toInitSquareId(color, coordinates);
	}
	
	public Movement move(String squareId) {
		Optional<Movement> optionalMove = possibleMoves.stream().filter(move -> move.getMoveToSquareId().equals(squareId)).findFirst();
		if (optionalMove.isPresent())
			setPositionSquareId(squareId);
		return optionalMove.orElse(null);
	}
	
	public List<Movement> evaluate(Board board){
		possibleMoves.clear();
		for(Move move : moves) {
			move.evaluate(board, this).stream().forEach(possibleMoves::add);
		}
		return possibleMoves;
	}
	
	protected void limitMoves(Board board, Movement move) {
		List<Movement> restrictTo = move.getRules().get(0).evaluateDirection(board, this, move.getDirection());
		restrictTo.addAll(move.getRules().get(0).evaluateDirection(board, this, move.getDirection().invert()));
		intersectLists(false, possibleMoves, restrictTo);
	}
	
	protected void intersectLists(boolean invert, List<Movement> l1, List<Movement> l2) {
		l1.removeAll(l1.stream().filter(move -> !invert ^ matchToSquare(move, l2)).collect(Collectors.toList()));
	}
	
	private boolean matchToSquare(Movement move, List<Movement> list) {
		return (list.stream().filter(listMove -> move.getMoveToSquareId().equals(listMove.getMoveToSquareId())).count() > 0);
	}

	public String getId() {
		return id;
	}

	public PieceName getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public String getPositionSquareId() {
		return positionSquareId;
	}

	public boolean isCaptured() {
		return captured;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public boolean hasMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public void setPositionSquareId(String positionSquareId) {
		this.positionSquareId = positionSquareId;
		captured = positionSquareId.equals("");
	}

	public void setCaptured(boolean captured) {
		this.captured = captured;
		if (captured) {
			positionSquareId = "";
		}
	}

	public Coordinates getCoords(api.chess.equipment.board.Board board) {
		return board.getSquare(positionSquareId).getCoordinates();
	}
}
