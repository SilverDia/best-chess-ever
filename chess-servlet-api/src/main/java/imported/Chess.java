package imported;

import imported.chess.ChessBoard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Chess
 */
@WebServlet("/Chess")
public class Chess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chess() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChessBoard board = generateFromJson();
		doAction(board);
		String newJson = generateNewJson(board);
		response.setContentType("application/json");
		response.getWriter().println(newJson);
	}
	
	private ChessBoard generateFromJson() {
		return null;
	}
	
	private void doAction(ChessBoard board) {
		board.doAction();
	}
	
	private String generateNewJson(ChessBoard board) {
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
