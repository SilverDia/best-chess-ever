package servlet;

import api.chess.equipment.board.Board;
import com.google.gson.Gson;

import java.io.IOException;
import javax.servlet.ServletException;
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

	private final static String ACTION = "action";
	private final static String ACTION_INIT_GAME = "init-game";

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter(ACTION).equals(ACTION_INIT_GAME)) {
			Board testboard = new Board();
			testboard.init();
			response.getWriter().append(new Gson().toJson(testboard));

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
