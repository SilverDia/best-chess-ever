var jsonObject;
var limitEntries = 20;

$(document).ready(function() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			jsonObject = JSON.parse(this.responseText);
			fill_table();
		}
	};
	xhttp.open("GET", "/ChessGame/HighscoreTable", true);
	xhttp.send();
});

function fill_table() {
	var entry = 0;
	for (entry; entry < Math.min(jsonObject.length, limitEntries); entry++) {
		document.getElementById('highscore-table').innerHTML += '<tr><td>'
				+ jsonObject[entry].name + '</td><td>'
				+ jsonObject[entry].moveCount + '</td><td>'
				+ jsonObject[entry].time + '</td></tr>';
	}
}

function setLimit(limit) {
	limitEntries = limit;
}