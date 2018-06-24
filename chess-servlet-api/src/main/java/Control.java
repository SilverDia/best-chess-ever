import com.google.common.base.Strings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/Control")
public class Control extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(Control.class.getName());


    public Control() {
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/overview/entryPage.jsp");
        //Checking for next servlet
        if (!Strings.isNullOrEmpty(request.getParameter("servlet"))) {
            //Check existence of servlet name --- else return entryPage.jsp
            String nextServlet = request.getParameter("servlet");
            HashMap<String, String> requiredParameters = new HashMap<>();
            //Setting up next servlet
            if (nextServlet.equals("CreateNewYearServlet")) {
                //Set other attributes

                //return dispatcher
                return dispatcher = request.getRequestDispatcher("/" + nextServlet);
            } else if (nextServlet.equals("SelectionInYearServlet") && hasEssentialParameters(request, "", requiredParameters)) {
                //Set required attributes of request parameters

                //Set other attributes

                //return dispatcher
                return dispatcher = request.getRequestDispatcher(nextServlet);
            } else if (nextServlet.equals("SelectionInYearServlet") && hasEssentialParameters(request, "", requiredParameters)) {
                //Set required attributes of request parameters

                //Set other attributes

                //return dispatcher
                return dispatcher = request.getRequestDispatcher("/" + nextServlet);
            }
        }
        return dispatcher;
    }





    private boolean hasEssentialParameters(HttpServletRequest request, String requiredParametersString, HashMap<String, String> requiredParameters) {
        String[] requiredParametersSplitted = requiredParametersString.split(",");
        Enumeration<String> parameterNames = request.getParameterNames();
        Arrays.stream(requiredParametersSplitted).forEach(parameterName -> {
            if (!Strings.isNullOrEmpty(request.getParameter(parameterName))) {
                requiredParameters.put(parameterName, request.getParameter(parameterName));
            }
        });


        return true;
    }
}

