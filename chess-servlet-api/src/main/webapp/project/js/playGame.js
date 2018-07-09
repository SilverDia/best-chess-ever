var gameObject;


$(document).ready(function () {
    loadDoc();
    buildBoard();


});


function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            gameObject = JSON.parse(this.responseText);
        }
    };
    xhttp.open(
        "GET",
        "http://localhost:8080/ChessGame/GameControl?action=init-game",
        false);
    xhttp.send();
}

function buildBoard() {
    var boardObject;
    var pieces;
    pieces = Object.assign(gameObject.player["WHITE"].freePieces, gameObject.player["BLACK"].freePieces);
    boardObject = gameObject.board.chessBoard;
    for (var rowId in boardObject) {
        var boardRowObject = boardObject[rowId];
        var boardRow;
        boardRow = document.createElement("div");
        boardRow.className = "board-table-row";

        var borderSquare;
        borderSquare = document.createElement("div");
        borderSquare.className = "board-square border";
        var newContent = document.createTextNode("Hi there and greetings!");
        borderSquare.appendChild(newContent);
        boardRow.appendChild(borderSquare);

        for (var squareId in boardRowObject) {
            var boardSquare;
            boardSquare = document.createElement("div");
            boardSquare.id = squareId;
            boardSquare.className = "board-table-square ";
            boardSquare.className += boardRowObject[squareId].color;
            if (!boardRowObject[squareId].vacant) {
                var image = document.createElement("img");
                image.src = pieces[boardRowObject[squareId].pieceId].imageUrl;
                boardSquare.appendChild(image);
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
}

