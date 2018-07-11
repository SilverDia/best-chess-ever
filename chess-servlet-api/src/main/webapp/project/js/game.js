var jsonObject = {};
var gameID;

function init_game(element) {
	var sender = element;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			jsonObject = JSON.parse(this.responseText);
			parse_json();
		}
	};
	xhttp.open("GET",
			"http://localhost:8080/ChessGame/GameControl?action=init-game",
			true);
	xhttp.send();
}

function parse_json() {
	gameID = jsonObject.gameId;
	document.getElementById("turnInfo").innerHTML = "ID: " + gameID;

	var i;
	for (i = 0; i < 8; i++) {
		for (square in jsonObject.board.chessBoard[i]) {
			if (jsonObject.board.chessBoard[i][square].pieceID !== "") {
				document
						.getElementById(jsonObject.board.chessBoard[i][square].squareId).innerHTML = "<img src=/ChessGame/project/resources/images/piece-sets/default/"
						+ jsonObject.board.chessBoard[i][square].pieceID
								.substring(
										0,
										jsonObject.board.chessBoard[i][square].pieceID.length - 2)
								.toLowerCase() + ".png\>";
			} else {
				document
						.getElementById(jsonObject.board.chessBoard[i][square].squareId).innerHTML = "";
			}
		}
	}
	setupOnclick('WHITE');
	setupOnclick('BLACK');
}

function setupOnclick(color) {
	for (pieceType in jsonObject.player[color].pieceSet) {
		for (piece in jsonObject.player[color].pieceSet[pieceType]) {
			if (typeof jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves !== 'undefined')
				document
						.getElementById(
								jsonObject.player[color].pieceSet[pieceType][piece].positionSquareId)
						.setAttribute(
								"onclick",
								"clickedPiece('" + color + "','" + pieceType
										+ "','" + piece + "')");
		}
	}
}

function clickedPiece(color, pieceType, piece) {
	var oldElements = document.getElementsByClassName("turn-suggestion");
	for (i = 0; i < oldElements.length; i++) {
		oldElements[i].classList.remove("capture");
		oldElements[i].classList.remove("valid-turn");
		oldElements[i].classList.remove("turn-suggestion");
		oldElements[i].removeAttribute("onclick");
	}

	for (move in jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves) {
		var element = document
				.getElementById(jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].moveToSquareId);
		element
				.setAttribute(
						"onclick",
						"madeMove('"
								+ piece
								+ "','"
								+ jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].moveToSquareId
								+ "')");

		if (element.innerHTML !== "") {
			element.classList.add("capture");
		} else {
			element.classList.add("valid-turn");
		}
		element.classList.add("turn-suggestion");
	}
}

function madeMove(pieceId, moveToSquareId) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			jsonObject = JSON.parse(this.responseText);
			parse_json();
		}
	};
	xhttp.open("GET",
			"http://localhost:8080/ChessGame/GameControl?action=execute-move&game-id="
					+ gameID + "&move-piece-id=" + pieceId
					+ "&move-to-square-id=" + moveToSquareId, true);
	xhttp.send();
}