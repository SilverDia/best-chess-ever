package servlet;

import api.chess.highscore.HighscoreHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = { "/HighscoreTable" })
public class Highscore extends HttpServlet {

	HighscoreHandler highscoreHandler = new HighscoreHandler();

	protected void doGetPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		StringBuffer requestUrl = request.getRequestURL();
		for (String line : readTxtFile("/resources/html/highscore.html")) {
			if (line.equals("!#highscore#!"))
				response.getWriter().println(createHighscoreTable(request.getParameterMap()));
			else
				response.getWriter().println(line);
		}
	}

	private String createHighscoreTable(Map<String, String[]> parameters) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < highscoreHandler.getHighscoreCount(); i++) {
			sb.append("\t\t<tr>\n");
			sb.append("\t\t\t<td>" + highscoreHandler.getName(i) + "</td>\n");
			sb.append("\t\t\t<td>" + highscoreHandler.getScore(i) + "</td>\n");
			sb.append("\t\t</tr>\n");
		}
		return sb.toString();
	}

	private List<String> readTxtFile(String path) throws IOException {
		return new ArrayList<>(Files.readAllLines(Paths.get(getServletContext().getRealPath(path))));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGetPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGetPost(request, response);
	}

}
