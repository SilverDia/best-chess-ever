package api.chess.highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighscoreHandler {

	private File file;
	private ArrayList<HighscoreEntry> highscores = new ArrayList<>();

	public HighscoreHandler(String path) {
		file = new File(path);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			getHighscores();
		}
	}

	public void addHighscoreEntry(String name, int time, int count) {
		highscores.add(new HighscoreEntry(name, time, count));
		sortHighscore();
		writeToFile();
	}

	private void getHighscores() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (br.ready()) {
				String line = br.readLine();
				String[] entry = line.split(";");
				addHighscoreEntry(entry[0], Integer.parseInt(entry[1]), Integer.parseInt(entry[2]));
			}
			sortHighscore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sortHighscore() {
		highscores.sort(new Comparator<HighscoreEntry>() {

			@Override
			public int compare(HighscoreEntry o1, HighscoreEntry o2) {
				if (o1.getMoveCount() != o2.getMoveCount())
					return o1.getMoveCount() - o2.getMoveCount();
				else
					return o1.getTime() - o2.getTime();
			}
		});
	}

	private void writeToFile() {

		// delete File Content
		try (FileWriter writer = new FileWriter(file)) {
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write highscores to file
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			bw.write("");
			for (HighscoreEntry e : highscores) {
				bw.append(e.getName() + ";" + e.getTime() + ";" + e.getMoveCount());
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<HighscoreEntry> getEntries() {
		return highscores;
	}

}
