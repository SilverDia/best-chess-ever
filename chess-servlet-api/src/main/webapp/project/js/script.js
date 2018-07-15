var navbarContent = '<a href="/ChessGame/project/html/index.html">Startseite</a>';
navbarContent += '<a href="/ChessGame/project/html/game.html">Spiel</a>';
navbarContent += '<a href="/ChessGame/project/html/highscore.html">Highscore</a>';
navbarContent += '<div class="dropdown"><a class="dropbtn" href="/DHBW-Projekt/resources/html/manual.html">Anleitung</a><div class="dropdown-content"><a href="/DHBW-Projekt/resources/html/manual.html#anchor-anleitung">Einleitung</a><a href="/DHBW-Projekt/resources/html/manual.html#anchor-zuege">Spielzüge</a><a href="/DHBW-Projekt/resources/html/manual.html#anchor-spielende">Spielende</a></div></div>';
navbarContent += '<a href="/ChessGame/project/html/impressum.html">Impressum</a>';
navbarContent += '<a href="javascript:void(0);" class="navbarIcon" onclick="toggleNavbar()"><i class="fa fa-bars"></i></a>';
document.getElementById("navbar").innerHTML = navbarContent;

// (On small screens) Toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon
function toggleNavbar() {
    var x = document.getElementById("navbar");
    if (x.className === "navbar") {
        x.className += " responsive";
    } else {
        x.className = "navbar";
    }
}

// pause button in game.html
function pause(){
    var button = document.getElementById("pause-button");
    if(button.className === 'pause-button'){
        button.className += ' paused';
    }else{
        button.className = 'pause-button';
    }
}

// toggle pawn-conversion in game.html
function toggle(string){
    var x = document.getElementById(string);
    if(x.className === "chessboard pawn-convertion-"+string){
        x.className += " invisible";
    }else{
        x.className = "chessboard pawn-convertion-"+string;
    }
}

// toggle the boxes in ending of manual.html
function toggleManualEnding(string){
    if(string === 'check'){
        document.getElementById('check').className = "manual-ending-grid-check-big";
        document.getElementById('check-heading').className = "";
        document.getElementById('check-text-short').className = "hidden";
        document.getElementById('check-text').className = "";
        document.getElementById("checkmate").className = "manual-ending-grid-checkmate-small-right";
        document.getElementById('checkmate-heading').className = "sideways";
        document.getElementById('checkmate-text-short').className = "hidden";
        document.getElementById('checkmate-text').className = "hidden";
        document.getElementById("patt").className = "manual-ending-grid-patt-small";
        document.getElementById('patt-heading').className = "sideways";
        document.getElementById('patt-text-short').className = "hidden";
        document.getElementById('patt-text').className = "hidden";
    }else if (string === 'checkmate'){
        document.getElementById("check").className = "manual-ending-grid-check-small";
        document.getElementById('check-heading').className = "sideways";
        document.getElementById('check-text-short').className = "hidden";
        document.getElementById('check-text').className = "hidden";
        document.getElementById("checkmate").className = "manual-ending-grid-checkmate-big";
        document.getElementById('checkmate-heading').className = "";
        document.getElementById('checkmate-text-short').className = "hidden";
        document.getElementById('checkmate-text').className = "";
        document.getElementById("patt").className = "manual-ending-grid-patt-small";
        document.getElementById('patt-heading').className = "sideways";
        document.getElementById('patt-text-short').className = "hidden";
        document.getElementById('patt-text').className = "hidden";
    }else if (string === 'patt'){
        document.getElementById("check").className = "manual-ending-grid-check-small";
        document.getElementById('check-heading').className = "sideways";
        document.getElementById('check-text-short').className = "hidden";
        document.getElementById('check-text').className = "hidden";
        document.getElementById("checkmate").className = "manual-ending-grid-checkmate-small-left";
        document.getElementById('checkmate-heading').className = "sideways";
        document.getElementById('checkmate-text-short').className = "hidden";
        document.getElementById('checkmate-text').className = "hidden";
        document.getElementById("patt").className = "manual-ending-grid-patt-big";
        document.getElementById('patt-heading').className = "";
        document.getElementById('patt-text-short').className = "hidden";
        document.getElementById('patt-text').className = "";  
    }
}
