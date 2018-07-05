package imported;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servlet implementation class ChessBoard
 */
@WebServlet(description = "Chess i guess", urlPatterns = { "/ChessBoard" })
public class ChessBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[][] startSetup = {
			{ "rook_black", "knight_black", "bishop_black", "queen_black", "king_black", "bishop_black", "knight_black",
					"rook_black" },
			{ "pawn_black", "pawn_black", "pawn_black", "pawn_black", "pawn_black", "pawn_black", "pawn_black",
					"pawn_black" },
			{ "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "" },
			{ "pawn_white", "pawn_white", "pawn_white", "pawn_white", "pawn_white", "pawn_white", "pawn_white",
					"pawn_white" },
			{ "rook_white", "knight_white", "bishop_white", "queen_white", "king_white", "bishop_white", "knight_white",
					"rook_white" } };
	
	private Map<String,String> stateToInfo = new HashMap<String,String>(){
		private static final long serialVersionUID = 1L;
		{
			put("turn_white","Weiss ist am Zug");
			put("turn_black","Schwarz ist am Zug");
			put("check_white","Weiss steht im Schach!");
			put("check_black","Schwarz steht im Schach!");
			put("checkmate_white","Weiss ist Schachmatt!");
			put("checkmate_black","Schwarz ist Schachmatt!");
		}
	};
			
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChessBoard() {
		super();
	}

	protected void doGetPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.getParameterMap().entrySet().forEach(entry ->
		// System.out.println("Key: " + entry.getKey() + ", Values: "
		// +
		// Arrays.asList(entry.getValue()).stream().collect(Collectors.joining(",
		// "))));

		for (String line : readTxtFile("/resources/html/game.html")) {
			if (line.equals("!#Schachbrett#!"))
				response.getWriter()
						.println(createTable(layOutFigures(request.getParameterMap()), request.getParameterMap()));
			else
				response.getWriter().println(line);
		}
	}

	private List<String> readTxtFile(String path) throws IOException {
		return Files.readAllLines(Paths.get(getServletContext().getRealPath(path))).stream()
				.collect(Collectors.toCollection(ArrayList<String>::new));
	}

	private String[][] layOutFigures(Map<String, String[]> parameters) {
		if (parameters.isEmpty())
			return startSetup;
		String[][] layout = { { "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "" } };

		for (int y = 0; y <= 7; y++) {
			for (int x = 0; x <= 7; x++) {
				layout[y][x] = parameters.get("" + x + y)[0];
			}
		}

		if (!parameters.get("moveto")[0].equals("")) {
			String fromCord = parameters.get("movefrom")[0];
			String toCord = parameters.get("moveto")[0];
			int fromX = Integer.parseInt(fromCord.substring(0, 1));
			int fromY = Integer.parseInt(fromCord.substring(1));
			int toX = Integer.parseInt(toCord.substring(0, 1));
			int toY = Integer.parseInt(toCord.substring(1));
			layout[toY][toX] = layout[fromY][fromX];
			layout[fromY][fromX] = "";
			parameters.get("movefrom")[0] = "";
			parameters.get("moveto")[0] = "";
		}

		return layout;
	}

	private String createTable(String[][] layout, Map<String, String[]> parameters) {
		StringBuilder sb = new StringBuilder();
		boolean weiss = true;
		String gamestate = (parameters.containsKey("gamestate") ? parameters.get("gamestate")[0] : "turn_white");

		List<String> matches = Collections.emptyList();
		if (parameters.containsKey("match"))
			matches = Arrays.asList(parameters.get("match"));

		sb.append("\t\t<label id=\"game_info\">" + stateToInfo.get(gamestate) + "</label>\n");
		sb.append("\t\t<table class=\"chessboard\">\n");
		for (int y = 0; y <= 7; y++) {
			sb.append("\t\t\t<tr>\n");
			for (int x = 0; x <= 7; x++) {
				sb.append(insertTile(x, y, weiss, layout, parameters, matches, gamestate));
				weiss ^= true;
			}
			sb.append("\t\t\t</tr>\n");
			weiss ^= true;
		}
		sb.append("\t\t</table>\n");
		sb.append("<form id=\"form\" name=\"data\" hidden>");
		for (int y = 0; y <= 7; y++) {
			for (int x = 0; x <= 7; x++) {
				sb.append("<input name=\"" + x + y + "\" value=\"" + layout[y][x] + "\"/>");
			}
		}
		sb.append("<input name=\"movefrom\" id=\"movefrom\" value=\""
				+ (parameters.containsKey("movefrom") ? parameters.get("movefrom")[0] : "") + "\"/>");
		sb.append("<input name=\"moveto\" id=\"moveto\" value=\""
				+ (parameters.containsKey("moveto") ? parameters.get("moveto")[0] : "") + "\"/>");
		sb.append("<input name=\"gamestate\" id=\"gamestate\" value=\"" + gamestate + "\"/>");
		sb.append("</form>");
		return sb.toString();
	}

	private String insertTile(int x, int y, boolean weiss, String[][] layout, Map<String, String[]> parameters,
                              List<String> matches, String gamestate) {
		StringBuilder sb = new StringBuilder();

		final String[] piece = layout[y][x].split("_");
		final int name = 0; // for easier reading of piece[] usage
		final int color = 1;

		// building td and img element
		final String tileColor = (matches.contains("" + x + y) ? (piece[name] != "" ? "capture" : "valid-turn")
				: (weiss ? "light" : "dark"));
		final String containsPiece = (piece[name].equals("") ? "" : piece[color] + " piece");
		final String isClickable = (matches.contains("" + x + y) ? "onclick=\"javascript:moveto" + "(" + x + "," + y + ")\""
				: "");
		final String isTurn = (piece[name] != "" && piece[color].equals(gamestate.split("_")[color])
				? "onclick=\"javascript:move" + piece[name] + "(" + x + "," + y + ")\"" : "");

		// building complete string
		sb.append("\t\t\t\t<td id=\"tile-" + x + y + "\" class=\"" + "chessboard-tile " + tileColor + " " + piece[name]
				+ " " + containsPiece + "\"" + isClickable + ">\n");
		if (piece[name] != "")
			sb.append("\t\t\t\t\t<img src=\"/DHBW-Projekt/resources/images/" + layout[y][x] + ".png\" " + isTurn
					+ "/>\n");
		sb.append("\t\t\t\t</td>\n");
		return sb.toString();
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
