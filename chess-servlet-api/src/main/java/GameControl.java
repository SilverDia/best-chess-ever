import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet("/GameControl")
public class GameControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(GameControl.class.getName());

    private final static String ACTION = "action";

    private final static String ACTION_INIT_GAME = "init-game";

    private final static String ACTION_EVALUATE_MOVEMENT = "evaluate-movement";
    private final static String PARAM_SQUARE_ID = "square-id";

    private final static String ACTION_MOVE = "move";
    private final static String PARAM_FROM = "from";
    private final static String PARAM_TO = "to";


    public GameControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = redirectRequest(request);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private RequestDispatcher redirectRequest(HttpServletRequest request) {
        //Default dispatcher if nothing´s matching
        RequestDispatcher dispatcher = request.getRequestDispatcher("../project/html/entrypage.html");
        //Checking for next servlet
        if (request.getParameter(ACTION) != null && ! request.getParameter(ACTION).equals("")) {
            //Check existence of servlet name --- else return entryPage.jsp
            String action = request.getParameter(ACTION);
            HashMap<String, String> requiredParameters = new HashMap<>();
            //Setting up next servlet
            if (action.equals(ACTION_INIT_GAME)) {
                //Set other attributes
                request.setAttribute("action", "init-game");
                //return dispatcher
                return dispatcher = request.getRequestDispatcher("/GetChessboardServlet");
            } else if (action.equals(ACTION_MOVE)) {
                //Set required attributes of request parameters

                //Set other attributes

                //return dispatcher
                return dispatcher = request.getRequestDispatcher("/GetChessboardServlet");
            }
        }
        return dispatcher;
    }
}

