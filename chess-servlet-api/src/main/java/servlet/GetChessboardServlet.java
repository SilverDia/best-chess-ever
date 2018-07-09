package servlet;

import api.chess.gameplay.game.Game;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
	private final static String ACTION_EXECUTE_MOVE = "execute-move";
	private final static String GAME_ID = "game-id";
	private final static String MOVE_PIECE_ID = "move-piece-id";
	private final static String MOVE_TO_SQUARE_ID = "move-to-square-id";


	HashMap<String, Game> games = new HashMap<>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChessboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (request.getServletContext().getAttribute("games") != null) {
			try {
				games = (HashMap<String, Game>) request.getServletContext().getAttribute("games");
			} catch (ClassCastException e) {
				LOG.log(Level.SEVERE, "Failed to load games hashmap from servlet context.\nSaved games cannot be loaded:\n" + e.getMessage(), e);
			} finally {
				games = new HashMap<>();
			}

		}

		if (request.getParameter(ACTION) != null && ! request.getParameter(ACTION).equals("")) {
			String action = request.getParameter(ACTION);
			if (action.equals(ACTION_INIT_GAME)) {
				Game game = new Game();
				games.put(game.gameId, game);
				request.getServletContext().setAttribute("games", games);
				game.init("white_player", "black_player");
				response.getWriter().append(new Gson().toJson(game));

			}
			else if (action.equals(ACTION_EXECUTE_MOVE)) {
				if ((request.getParameter(GAME_ID) != null && ! request.getParameter(GAME_ID).equals("")) &&
						(request.getParameter(MOVE_PIECE_ID) != null && ! request.getParameter(MOVE_PIECE_ID).equals("")) &&
						(request.getParameter(MOVE_TO_SQUARE_ID) != null && ! request.getParameter(MOVE_TO_SQUARE_ID).equals(""))) {
					String gameId = request.getParameter(GAME_ID);
					if (games.containsKey(gameId)) {
						Game game = games.get(gameId);
						game.executeMove(request.getParameter(MOVE_PIECE_ID), request.getParameter(MOVE_TO_SQUARE_ID));
						response.getWriter().append(new Gson().toJson(game));

					}
				}
			}
		} else {
			response.getWriter().append(games.keySet().toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
