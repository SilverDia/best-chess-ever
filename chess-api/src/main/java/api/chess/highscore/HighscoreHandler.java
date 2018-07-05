package api.chess.highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class HighscoreHandler {

	private File file;
	private String highscorePath = "C:\\Users\\mahu\\Documents\\highscore.txt";
	private ArrayList<HighscoreEntry> highscores = new ArrayList<>();
	private String[] entry = new String[2];

	private String name = "default";
	private int score;

	public HighscoreHandler() {
		file = new File(highscorePath);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			getHighscores();
			// for (HighscoreEntry e : highscores) {
			// System.out.println(e.getName());
			// System.out.println(e.getScore());
			// }
		}
	}

	private void addHighscoreEntry(String name, int score) {
		highscores.add(new HighscoreEntry(name, score));
	}

	public void getHighscores() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (br.ready()) {
				String line = br.readLine();
				entry = line.split(";");
				addHighscoreEntry(entry[0], Integer.parseInt(entry[1]));
			}
			sortHighscore();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sortHighscore() {
		highscores.sort(new Comparator<HighscoreEntry>() {

			@Override
			public int compare(HighscoreEntry o1, HighscoreEntry o2) {
				return o1.getScore() - o2.getScore();
			}
		});
	}

	public void writeToFile() {

		// delete File Content
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		// write highscores to file
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			bw.write("");
			for (HighscoreEntry e : highscores) {
				bw.append(e.getName() + ";" + e.getScore());
				bw.newLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
		addHighscoreEntry(this.name, this.score);
		sortHighscore();
		writeToFile();

		System.out.println("New Highscores are: ");
		for (HighscoreEntry e : highscores) {
			System.out.println(e.getName());
			System.out.println(e.getScore());
		}
		System.out.println();
	}

	public int getHighscoreCount() {
		return highscores.size();
	}

	public String getName(int i) {
		return highscores.get(i).getName();
	}

	public int getScore(int i) {
		return highscores.get(i).getScore();
	}

}
