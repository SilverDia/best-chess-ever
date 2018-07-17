package api.chess.highscore;

public class HighscoreEntry {

	private final String name;
	private final int time;
	private final int moveCount;

	public HighscoreEntry(String name, int time, int count) {
		this.name = name;
		this.time = time;
		this.moveCount = count;
	}

	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	public int getMoveCount() {
		return moveCount;
	}

}
