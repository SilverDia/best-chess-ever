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
	var pawn = document.getElementById('tile-' + x + y);
	if (pawn.classList.contains('black'))
		testLine(x, y, 0, 1, false);
	if (pawn.classList.contains('white'))
		testLine(x, y, 0, -1, false);
	setSelected(x, y);
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
	if (inRange(x + dx, y + dy) && testTile(x, y, x + dx, y + dy)
			&& testFurther) {
		if (dx != 0)
			dx += dx / Math.abs(dx);
		if (dy != 0)
			dy += dy / Math.abs(dy);
		testLine(x, y, dx, dy, testFurther);
	}
}

function testTile(ox, oy, x, y) {
	var tile = document.getElementById('tile-' + x + y);
	var selected = document.getElementById('tile-' + ox + oy);
	var canContinue = false;
	if (!tile.classList.contains('piece') || tile.classList.contains('black')
			&& selected.classList.contains('white')
			|| tile.classList.contains('white')
			&& selected.classList.contains('black')) {
		var input = document.createElement('input');
		input.name = 'match';
		input.value = '' + x + y;
		document.getElementById('form').appendChild(input);
		canContinue = !tile.classList.contains('piece');
	}
	return canContinue;
}

function moveto(x, y) {
	var input = document.getElementById('moveto');
	input.value = String(x) + String(y);
	var gamestate = document.getElementById('gamestate');
	gamestate.value = (gamestate.value == 'turn_white' ? 'turn_black' : 'turn_white');
	document.data.submit();
}

function inRange(x, y) {
	return x >= 0 && y >= 0 && x <= 7 && y <= 7;
}
