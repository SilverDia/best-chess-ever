package servlet;

import api.chess.gameplay.game.Game;
import api.chess.highscore.HighscoreHandler;
import api.chess.player.Player;
import api.config.PieceConfig;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetChessboardServlet
 */
@WebServlet("/GetChessboardServlet")
public class GetChessboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(GetChessboardServlet.class.getName());

	private final static String ACTION = "action";
	private final static String ACTION_INIT_GAME = "init-game";
	private final static String BLACK_PLAYER_NAME = "black-player-name";
	private final static String WHITE_PLAYER_NAME = "white-player-name";
	private final static String ACTION_EXECUTE_MOVE = "execute-move";
	private final static String GAME_ID = "game-id";
	private final static String MOVE_PIECE_ID = "move-piece-id";
	private final static String MOVE_TO_SQUARE_ID = "move-to-square-id";
	private final static String PROMOTE_TO_PIECE = "promote-to-piece";

	HashMap<String, Game> games = new HashMap<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetChessboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getServletContext().getAttribute("games") != null) {
			try {
				games = (HashMap<String, Game>) request.getServletContext().getAttribute("games");
			} catch (ClassCastException e) {
				LOG.log(Level.SEVERE,
						"Failed to load games hashmap from servlet context.\nSaved games cannot be loaded:\n"
								+ e.getMessage(),
						e);
			}

		}

		if (request.getParameter(ACTION) != null && !request.getParameter(ACTION).equals("")) {
			String action = request.getParameter(ACTION);
			if (action.equals(ACTION_INIT_GAME)) {
				String nameWhitePlayer = "WHITE_PLAYER_" + String.valueOf(new Date().getTime());
				String nameBlackPlayer = "BLACK_PLAYER_" + String.valueOf(new Date().getTime());
				if (request.getParameter(BLACK_PLAYER_NAME) != null
						&& !request.getParameter(BLACK_PLAYER_NAME).equals("")) {
					nameBlackPlayer = request.getParameter(BLACK_PLAYER_NAME);
				}
				if (request.getParameter(WHITE_PLAYER_NAME) != null
						&& !request.getParameter(WHITE_PLAYER_NAME).equals("")) {
					nameWhitePlayer = request.getParameter(WHITE_PLAYER_NAME);
				}
				Game game = new Game();
				games.put(game.gameId, game);
				request.getServletContext().setAttribute("games", games);
				game.init(nameWhitePlayer, nameBlackPlayer);
				response.getWriter().append(new Gson().toJson(game));

			} else if (action.equals(ACTION_EXECUTE_MOVE)) {
				if ((request.getParameter(GAME_ID) != null && !request.getParameter(GAME_ID).equals(""))
						&& (request.getParameter(MOVE_PIECE_ID) != null
								&& !request.getParameter(MOVE_PIECE_ID).equals(""))
						&& (request.getParameter(MOVE_TO_SQUARE_ID) != null
								&& !request.getParameter(MOVE_TO_SQUARE_ID).equals(""))) {
					String gameId = request.getParameter(GAME_ID);
					if (games.containsKey(gameId)) {
						Game game = games.get(gameId);

						String promotion = (request.getParameter(PROMOTE_TO_PIECE) != null
								&& !request.getParameter(PROMOTE_TO_PIECE).equals(""))
										? request.getParameter(PROMOTE_TO_PIECE)
										: "";

						game.executeMove(request.getParameter(MOVE_PIECE_ID), request.getParameter(MOVE_TO_SQUARE_ID),
								promotion);
						
						if (game.getTurnHistory().peekLast().isCheckmated())
							setHighscore(request, game);

						String responseJson = new Gson().toJson(game);
						// debug
						// System.out.println(responseJson);
						response.getWriter().append(responseJson);
					}
				}
			}
		} else {
			response.getWriter().append(games.keySet().toString());
		}
	}
	
	private void setHighscore(HttpServletRequest request, Game game) {
		HighscoreHandler highscoreHandler = null;
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
		Player winner = game.getPlayer().get((game.getActivePlayer().equals(PieceConfig.Color.WHITE)?PieceConfig.Color.BLACK:PieceConfig.Color.WHITE));
		highscoreHandler.addHighscoreEntry(winner.getName(), winner.getFullTime(), winner.getTurnCounter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
