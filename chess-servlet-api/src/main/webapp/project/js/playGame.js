var testObject = {};



$(document).ready(function() {
	for (var i = 0; i < 10; i++) {
		var boardRow;
		boardRow = document.createElement("div");
		boardRow.setAttribute("class", "board-table-row");
		var borderSquare;
		borderSquare = document.createElement("div");
		borderSquare.setAttribute("class", "board-square border");
		var newContent = document.createTextNode("Hi there and greetings!");
		borderSquare.appendChild(newContent);
		boardRow.appendChild(borderSquare);

		for (var j = 0; j < 8; j++) {
			var boardSquare;
			boardSquare = document.createElement("div");
			if (((j+i) % 2) === 0) {
				boardSquare.setAttribute("class", "board-square white");
			} else {
				boardSquare.setAttribute("class", "board-square black");
			}
			boardRow.appendChild(boardSquare);
		}
		var borderSquare2;
		borderSquare2 = document.createElement("div");
		borderSquare2.setAttribute("class", "board-square border");
		var newContent2 = document.createTextNode("Hi there and greetings!");
		borderSquare2.appendChild(newContent2);
		boardRow.appendChild(borderSquare2);
		document.getElementById("board").appendChild(boardRow);
	}
	loadDoc();
	});



function loadDoc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			testObject = JSON.parse(this.responseText);
		}
	};
	xhttp.open(
		"GET",
		"http://localhost:8080/ChessGame/GameControl?action=init-game",
		true);
	xhttp.send();
}

