package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EvaluateMovementsServlet
 */
@WebServlet("/EvaluateMovementsServlet")
public class EvaluateMovementsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String ACTION = "action";
	private final static String ACTION_EVALUATE = "evaluate";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluateMovementsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter(ACTION).equals(ACTION_EVALUATE)) {

			response.getWriter().append("Evaluating...");

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
