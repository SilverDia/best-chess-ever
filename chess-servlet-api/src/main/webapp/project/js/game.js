var jsonObject = {};
var gameID;

$(document).ready(function () {
    $('#grid-game').toggle();
    $('#start-new-game').click(function () {
        var whitePlayerName = $('#init-player-white').val();
        var blackPlayerName = $('#init-player-black').val();
        if (whitePlayerName && blackPlayerName) {
            init_game(gameID, whitePlayerName, blackPlayerName);
            $('#init-layer').toggle();
            $('#grid-game').toggle();

            $('#pause-button').click(function () {
                stopTimer(true);
            });
            $('#pause-layer').click(function () {
                startTimer(true);
            });
        }
    });
});

function init_game(id, whiteName, blackName) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            jsonObject = JSON.parse(this.responseText);
            parse_json();
            updatePlayerInfo();
            startTimer();
        }
    };
    xhttp.open("GET",
        "/ChessGame/GetChessboardServlet?action=init-game&game-id=" + gameID +
        "&white-player-name=" + whiteName +
        "&black-player-name=" + blackName,
        true);
    xhttp.send();
}

function parse_json() {
    clearOldSuggestions();
    clearLastMove();
    gameID = jsonObject.gameId;

    var lastEntry = jsonObject.turnHistory.length - 1;
    $('#turn-info-container').text(jsonObject.player[activePlayer].name + " ist am Zug!");
    if (typeof jsonObject.turnHistory[lastEntry].message !== 'undefined')
        document.getElementById('game-log-textarea').innerHTML = jsonObject.turnHistory[lastEntry].message + '\n' + document.getElementById('game-log-textarea').innerHTML;
    if (typeof jsonObject.turnHistory[lastEntry].movement !== 'undefined') {
        document.getElementById(jsonObject.turnHistory[lastEntry].movement.moveFromSquareId).classList.add("last-turn");
        document.getElementById(jsonObject.turnHistory[lastEntry].movement.moveToSquareId).classList.add("last-turn");
    }

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
                    .getElementById(jsonObject.player[color].pieceSet[pieceType][piece].positionSquareId).firstChild
                    .setAttribute("onclick", "clickedPiece('" + color
                        + "','" + pieceType + "','" + piece + "')");
        }
    }
}

function clickedPiece(color, pieceType, piece) {
    clearOldSuggestions();

    var selected = document
        .getElementById(jsonObject.player[color].pieceSet[pieceType][piece].positionSquareId);
    selected.classList.add("click-display");
    selected.classList.add("chosen");

    for (move in jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves) {
        var element = document
            .getElementById(jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].moveToSquareId);
        element
            .setAttribute(
                "onclick",
                "checkPawnProm(this, '"
                + piece
                + "','"
                + jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].moveToSquareId
                + "')");

        if (jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].rules[jsonObject.player[color].pieceSet[pieceType][piece].possibleMoves[move].rules.length - 1] == "CAPTURE_MOVE") {
            element.classList.add("capture");
        } else {
            element.classList.add("valid-turn");
        }
        element.classList.add("click-display");
    }
}

function clearOldSuggestions() {
    $('.click-display').toggleClass("click-display capture valid-turn chosen",
        false).removeAttr("onclick");
}

function checkPawnProm(sender, pieceId, moveToSquareId) {
    if ($(sender.parentElement).hasClass("promotion-row-white") && pieceId.includes("PAWN_WHITE")) {
        toggle("pawn-promotion-white");
        $('#pawn-promotion-white .chessboard-tile').click(function () {
            var promPiece = this.id;
            toggle("pawn-promotion-white");
            madeMove(pieceId, moveToSquareId, promPiece);
        });
    } else if ($(sender.parentElement).hasClass("promotion-row-black") && pieceId.includes("PAWN_BLACK")) {
        toggle("pawn-promotion-black");
        $('#pawn-promotion-black .chessboard-tile').click(function () {
            var promPiece = this.id;
            toggle("pawn-promotion-black");
            madeMove(pieceId, moveToSquareId, promPiece);
        });
    }
    else {
        madeMove(pieceId, moveToSquareId);
    }
}

function clearLastMove() {
    $('.last-turn').toggleClass("last-turn", false);
}


function madeMove(pieceId, moveToSquareId, promPiece) {
    var request;
    request = "/ChessGame/GetChessboardServlet?action=execute-move&game-id="
        + gameID
        + "&move-piece-id="
        + pieceId
        + "&move-to-square-id=" + moveToSquareId
        + "&duration=" + secTotal;
    if (promPiece) {
        promPiece = promPiece.substring(promPiece.lastIndexOf("-") + 1);
        request += "&promote-to-piece=" + promPiece;
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            jsonObject = JSON.parse(this.responseText);
            parse_json();
            updatePlayerInfo();
            startTimer();
        }
    };
    xhttp
        .open(
            "GET",
            request, true);
    xhttp.send();
    stopTimer();
}

var secTotal = 0;
var t;
var timerStatus = 0;

function runTimer() {
    secTotal++;
    formatTime();
    t = setTimeout(runTimer, 1000);
}

function startTimer(paused) {
    if (!paused) {
        stopTimer();
    } else {
        $('.grid-game').slideToggle(500);
        $('#pause-layer').slideToggle(500);
    }
    timerStatus = 1;
    runTimer();
}

function stopTimer(pause) {
    clearTimeout(t);
    timerStatus = 0;

    if (!pause) {
        secTotal = 0;
    } else {
        $('.grid-game').slideToggle(500);
        $('#pause-layer').slideToggle(500);
    }
    formatTime();
}

function formatTime() {
    var m;
    var s;
    m = Math.floor(secTotal / 60);
    s = secTotal % 60;

    $('#timer-' + (jsonObject.activePlayer).toLowerCase())
        .text(checkTime(m) + ":" + checkTime(s));
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i
    }
    // add zero in front of numbers < 10
    return i;
}

function updatePlayerInfo() {
    $.each(jsonObject.player, function (i, v) {
        var color = i.toLowerCase();
        var $playerInfo = $('#player-info-container-' + color);
        $playerInfo.find('.name-info').text(v.name);
        $playerInfo.find('.turns-complete').text(v.turnCounter);
        $playerInfo.find('.time-complete').text(v.durationFullSecs);
        $playerInfo.find('.captured-pieces').children("").remove();
        $.each(v.capturedPieces, function (pi, pv) {
           $playerInfo.find('.captured-pieces').append($(document.createElement("div"))
               .addClass('captured-piece')
               .addClass(pi.substring(0, pi.lastIndexOf("_")).toLowerCase())
               .append($(document.createElement("img"))
                   .attr("src", "/ChessGame/project/resources/images/piece-sets/default/" +
                       pi.substring(0, pi.lastIndexOf("_")).toLowerCase() + ".png")));
        });
    });
}