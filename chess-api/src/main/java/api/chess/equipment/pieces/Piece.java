package api.chess.equipment.pieces;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import api.chess.equipment.board.Board;
import api.chess.equipment.board.Coordinates;
import api.chess.gameplay.rules.Move;
import api.chess.gameplay.rules.Movement;
import api.chess.gameplay.rules.Movement.Restriction;
import api.config.BoardConfig;
import api.config.GameConfig;
import api.config.MovementRuleConfig;
import api.config.PieceConfig.Color;
import api.config.PieceConfig.PieceName;

public abstract class Piece {
	transient Logger LOG = Logger.getLogger(Piece.class.getName());

	String id; // to identify each figure --- type_color_number --- example:
				// Pawn_W_3 /
				// Knight_B_1

	transient PieceName name;
	transient Color color;

	String positionSquareId;
	transient boolean captured = false;
	transient boolean moved = false;

	transient List<Move> moves;

	private List<Movement> possibleMoves;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public Piece init(int id, Color color) {
		this.color = color;
		this.id = name.toString() + "_" + color.toString() + "_" + String.valueOf(id);

		moves = MovementRuleConfig.getMoves(name);
		return this;
	}

	public String initPosition(Coordinates coordinates, int id, int scale) {
		coordinates.setX(coordinates.getX() + (id * scale));
		return BoardConfig.toInitSquareId(color, coordinates);
	}

	public Movement move(String squareId) {
		Optional<Movement> optionalMove = getPossibleMoves().stream()
				.filter(move -> move.getMoveToSquareId().equals(squareId)).findFirst();
		if (optionalMove.isPresent())
			setPositionSquareId(squareId);
		return optionalMove.orElse(null);
	}

	public Movement getMoveWithDestination(String squareId) {
		return possibleMoves.stream().filter(move -> move.getMoveToSquareId().equals(squareId)).findFirst()
				.orElse(null);
	}

	public List<Movement> evaluate(Board board) {
		possibleMoves = new ArrayList<>();
		if (!captured)
			for (Move move : moves)
				move.evaluate(board, this).stream().forEach(getPossibleMoves()::add);

		return possibleMoves;
	}

	protected void limitMoves(Board board, Movement move) {
		List<Movement> restrictTo = move.getRules().get(0).evaluateDirection(board, this, move.getDirection());
		restrictTo.addAll(move.getRules().get(0).evaluateDirection(board, this, move.getDirection().invert()));
		
		setPossibleMoves(GameConfig.intersectLists(getPossibleMoves(), restrictTo, condition -> true));//no conditions
	}

	public void removeInvalid() {
		if (possibleMoves != null) {
			possibleMoves.removeAll(
					possibleMoves.stream().filter(move -> move.getBlockedBy() != null || Restriction.RESTRICT_KING.equals(move.getRestriction())).collect(Collectors.toList()));
		if (possibleMoves.isEmpty())
			possibleMoves = null;
		}
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

	public List<Movement> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(List<Movement> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}
}
