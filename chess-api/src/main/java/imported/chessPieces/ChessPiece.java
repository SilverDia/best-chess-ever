package imported.chessPieces;

public abstract class ChessPiece {
	
	final protected String name;
	
	public ChessPiece (String piece, String color) {
		this.name = piece + "_" + color;
	}
	
	public abstract void findValidPositions();
	
	protected void checkCombination() {
		
	}

	protected void checkLine() {
		
	}
	
	protected void checkPosition() {
		
	}
	
}
