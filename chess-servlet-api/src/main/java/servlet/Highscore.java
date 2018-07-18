package servlet;

import api.chess.gameplay.game.Game;
import api.chess.highscore.HighscoreHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@WebServlet(urlPatterns = { "/HighscoreTable" })
public class Highscore extends HttpServlet {

	HighscoreHandler highscoreHandler;

	protected synchronized void doGetPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getServletContext().getAttribute("highscore") != null) {
			try {
				highscoreHandler = (HighscoreHandler) request.getServletContext().getAttribute("highscore");
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}

		if (highscoreHandler == null) {
			highscoreHandler = new HighscoreHandler(
					getServletContext().getRealPath("/ChessGame").replaceAll("ChessGame", "high.scores"));
			request.getServletContext().setAttribute("highscore", highscoreHandler);
		}

		response.getWriter().append(new Gson().toJson(highscoreHandler.getEntries()));
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
