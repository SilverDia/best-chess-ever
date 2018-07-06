function movebishop(x, y) {
	testCombination(x, y, 1, 1, true);
	setSelected(x, y);
}

function moveking(x, y) {
	testCombination(x, y, 1, 1, false);
	testCombination(x, y, 1, 0, false);
	setSelected(x, y);
}

function moveknight(x, y) {
	testCombination(x, y, 2, 1, false);
	setSelected(x, y);
}

function movepawn(x, y) {
	var direction;
	var pawn = document.getElementById('tile-' + x + y);
	if (pawn.classList.contains('black'))
		direction = 1;
	if (pawn.classList.contains('white'))
		direction = -1;

	if (testTile(x, y, x, y + direction, false))
		testTile(x, y, x, y + direction, true);
	pawn_specialMoves(x, y, direction);
	setSelected(x, y);
}

function pawn_specialMoves(x, y, direction) {
	if (!testTile(x, y, x + 1, y + direction, false))
		testTile(x, y, x + 1, y + direction, true);
	if (!testTile(x, y, x - 1, y + direction, false))
		testTile(x, y, x - 1, y + direction, true);
	if (direction == 1 && y == 7) {
		document.getElementById(String(x) + String(y)).value = prompt(
				"Select Chesspiece", "queen")
				+ '_black';
	}
	if (direction == -1 && y == 0) {
		document.getElementById(String(x) + String(y)).value = prompt(
				"Select Chesspiece", "queen")
				+ '_white';
	}
	if (direction == 1 && y == 1 && testTile(x, y, x, y + 1, false)
			&& testTile(x, y, x, y + 2, false))
		testTile(x, y, x, y + 2, true);
	if (direction == -1 && y == 6 && testTile(x, y, x, y - 1, false)
			&& testTile(x, y, x, y - 2, false))
		testTile(x, y, x, y - 2, true);
}

function movequeen(x, y) {
	testCombination(x, y, 1, 1, true);
	testCombination(x, y, 1, 0, true);
	setSelected(x, y);
}

function moverook(x, y) {
	testCombination(x, y, 1, 0, true);
	setSelected(x, y);
}

function setSelected(x, y) {
	var input = document.getElementById('movefrom');
	input.value = String(x) + String(y);
	document.data.submit();
}

function testCombination(x, y, dx, dy, testFurther) {
	testLine(x, y, dx, dy, testFurther);
	testLine(x, y, -dx, dy, testFurther);
	testLine(x, y, dx, -dy, testFurther);
	testLine(x, y, -dx, -dy, testFurther);
	testLine(x, y, dy, dx, testFurther);
	testLine(x, y, dy, -dx, testFurther);
	testLine(x, y, -dy, dx, testFurther);
	testLine(x, y, -dy, -dx, testFurther);
}

function testLine(x, y, dx, dy, testFurther) {
	if (testTile(x, y, x + dx, y + dy, true) && testFurther) {
		if (dx != 0)
			dx += dx / Math.abs(dx);
		if (dy != 0)
			dy += dy / Math.abs(dy);
		testLine(x, y, dx, dy, testFurther);
	}
}

function testTile(ox, oy, x, y, log) {
	var tile = document.getElementById('tile-' + x + y);
	var origin = document.getElementById('tile-' + ox + oy);
	var canContinue = false;
	if (inRange(x, y)) {
		if (!tile.classList.contains('piece')
				|| tile.classList.contains('black')
				&& origin.classList.contains('white')
				|| tile.classList.contains('white')
				&& origin.classList.contains('black')) {
			if (log) {
				var input = document.createElement('input');
				input.name = 'match';
				input.value = '' + x + y;
				document.getElementById('form').appendChild(input);
			}
			canContinue = !tile.classList.contains('piece');
		}
	}
	return canContinue;
}

function moveto(x, y) {
	var input = document.getElementById('moveto');
	input.value = String(x) + String(y);
	var gamestate = document.getElementById('gamestate');
	// checkCheck(gamestate.split('_')[1], false);
	gamestate.value = (gamestate.value == 'turn_white' ? 'turn_black'
			: 'turn_white');
	document.data.submit();
}

function checkCheck(color, nextMove) {
	var from = document.getElementById('movefrom');
	var to = document.getElementById('movefrom');
	var fromtile = document.getElementById('tile-' + from.value);
	var totile = document.getElementById('tile-' + to.value);
}

function inRange(x, y) {
	return x >= 0 && y >= 0 && x <= 7 && y <= 7;
}
