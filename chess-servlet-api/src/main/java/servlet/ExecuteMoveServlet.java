package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class GetChessboardServlet
 */
@WebServlet("/ExecuteMoveServlet")
public class ExecuteMoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static String ACTION = "action";
	private final static String ACTION_MOVE = "move";

	private final static String PLAYER = "player";
	private final static String PLAYER_WHITE = "white";
	private final static String PLAYER_BLACK = "black";

	private final static String FROM = "from";
	private final static String TO = "to";


    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteMoveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter(ACTION).equals(ACTION_MOVE)) {

			response.getWriter().append("Moving...");

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
