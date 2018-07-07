var gameObject = {};

$(document).ready(function() {
	loadDoc();
	$(window).resize(function() {
		stayFuckingQuadratic();
	});
	stayFuckingQuadratic();
});

function loadDoc() {
	$
			.getJSON(
					"http://localhost:8080/ChessGame/GetChessboardServlet?action=init-game",
					function(data, status) {
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
	// $.ajax({
	// url : '/web-application/admin/html/nav-side.html',
	// dataType : "html"
	// }).done(function(data) {
	// templates = $.parseHTML(data);
	// buildMonthSelection(2018);
	// buildNavSection('home', 'Home', 'home');
	// buildNavSection('calendar', 'Kalender', 'calendar');
	// buildNavSection('billings', 'Abrechnungen', 'billing');
	// buildNavSection('customers', 'Kundenübersicht', 'horse');
	// });
}

function buildBoard() {
	var boardTable = $('#chessboard tbody');
	headerRow = function(position) {
		var $boardRow = $(document.createElement('tr'));
		$boardRow.attr("id", "board-border-" + position);
		$boardRow.addClass("board-border");
		for (var i = 0; i < 10; i++) {
			if (i === 0 || i === 9) {
				$boardRow.append($(document.createElement('td')));
			} else {
				$boardRow.append($(document.createElement('td')).text(
						String.fromCharCode(64 + i)).addClass(
						"board-border-cell"));
			}
		}
		return $boardRow;
	}
	boardTable.append(headerRow("bottom"));
	$.each(gameObject.board.chessBoard, function(i, row) {
		boardTable.prepend($(document.createElement('tr')).attr('id',
				'board-row-' + (i + 1)).append(
				function(j, ht) {
					var squares = [];
					$.each(row, function(id, square) {
						squares.push($(document.createElement('td')).attr('id',
								id.toLowerCase())
								.addClass(
										"chessboard-tile "
												+ square.color.toLowerCase()));
					});
					return squares;
				}))
	})
	boardTable.prepend(headerRow("top"));
}

var s = 0;
var m = 0;
var t;
var timer_is_on = 0;

function runTimer() {
	s = s + 1;
	formatTimer(s, m)
	t = setTimeout(runTimer, 1000);
}

function startTimer() {
	if (!timer_is_on) {
		timer_is_on = 1;
		runTimer();
	}
}

function stopTimer() {
	clearTimeout(t);
	timer_is_on = 0;
	s = 0;
	m = 0;
	formatTimer(s, m)
}
function formatTimer(i, j) {
	if (i === 60) {
		i = 0;
		j += 1;
	}

	$('.grid-time.active-time').text(checkTime(j) + ":" + checkTime(i));
}
function checkTime(i) {
	if (i < 10) {
		i = "0" + i
	}
	; // add zero in front of numbers < 10
	return i;
}

function changePlayer() {

	stopTimer();
	$('.grid-time').toggleClass('active-time inactive-time');
	$('.grid-player').toggleClass('active-player inactive-player');
	$('.turn-info-content')
			.text(
					"Spieler " + $('.grid-player.active-player').text()
							+ " ist am Zug");
	startTimer();
}
