var gameObject = {};

$(document).ready(function () {
    loadDoc();
    $(window).resize(function () {
        stayFuckingQuadratic();
    });
    stayFuckingQuadratic();
});

function loadDoc() {
    $
        .getJSON(
            "http://localhost:8080/ChessGame/GetChessboardServlet?action=init-game",
            function (data, status) {
                if (status === "success") {
                    gameObject = data;
                    buildSite();
                }
            });
}

function stayFuckingQuadratic() {
    $('.square').outerHeight($('.square').outerWidth());
}

function buildSite() {
    buildBoard();
    stayFuckingQuadratic();

    $('.grid-time img.time-button').click(function () {
        if ($(this).hasClass("time-pause")) {
            stopTimer(true);
            $(this).toggleClass("time-pause time-continue")
        } else if ($(this).hasClass("time-continue")) {
            startTimer(true);
            $(this).toggleClass("time-pause time-continue")
        }
    });
    $('#pause-layer').click(function () {
        $('.grid-time img.time-button').click();
    })
}

function buildBoard() {
    var boardTable = $('#chessboard tbody');
    headerRow = function (position) {
        var $boardRow = $(document.createElement('tr'));
        $boardRow.attr("id", "board-border-" + position);
        $boardRow.addClass("board-border board-border-row");
        for (var i = 0; i < 10; i++) {
            if (i === 0 || i === 9) {
                $boardRow.append($(document.createElement('td'))
                    .addClass("board-border-cell board-border-row board-border-col"));
            } else {
                $boardRow.append($(document.createElement('td'))
                    .text(String.fromCharCode(64 + i))
                    .addClass("board-border-cell board-border-row"));
            }
        }
        return $boardRow;
    };
    boardTable.append(headerRow("bottom"));
    $.each(gameObject.board.chessBoard, function (i, row) {
        boardTable.prepend($(document.createElement('tr')).attr('id',
            'board-row-' + (++i)).append(
            function (j, ht) {
                var squares = [];
                squares.push($(document.createElement('td')).addClass("board-border-cell board-border-col").text(i));
                $.each(row, function (id, square) {
                    squares.push($(document.createElement('td')).attr('id', id.toLowerCase())
                        .addClass("chessboard-tile " + square.color.toLowerCase()));
                });
                squares.push($(document.createElement('td'))
                    .addClass("board-border-cell board-border-col").text(i));
                return squares;
            }))
    });
    boardTable.prepend(headerRow("top"));
}

var s = 0;
var m = 0;
var t;
var timer_is_on = 0;

function runTimer() {
    s = s + 1;
    formatTimer(s, m);
    t = setTimeout(runTimer, 1000);
}

function startTimer(pause) {
    if (!timer_is_on) {
        timer_is_on = 1;
        runTimer();
        if (pause) {
            $('#pause-layer').slideToggle("500");
        }
    }
}

function stopTimer(pause) {
    clearTimeout(t);
    timer_is_on = 0;

    if (!pause) {
        $('.active-time img.time-button').toggleClass("time-pause time-continue",false);
        s = 0;
        m = 0;
    } else {
        $('#pause-layer').slideToggle("500");
    }
    formatTimer(s, m)
}

function formatTimer() {
    if (s === 60) {
        s = 0;
        m += 1;
    }

    $('.grid-time.active-time span').text(checkTime(m) + ":" + checkTime(s));
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i
    }
     // add zero in front of numbers < 10
    return i;
}

function triggerConversion(player) {
    $('#pawn-conversion-' + player).slideToggle(500);
}

function changePlayer() {

    stopTimer();
    $('.grid-time').toggleClass('active-time inactive-time');
    $('.active-time img.time-button').toggleClass("time-pause",true);

    $('.player-content-block').toggleClass('active-player inactive-player');
    $('.turn-info-content')
        .text(
            "Spieler " + $('.grid-player.active-player .grid-name').text()
            + " ist am Zug");
    startTimer();
}
