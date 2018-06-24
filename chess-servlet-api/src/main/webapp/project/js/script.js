var navbarContent = '<a href="/WebApplication/dhbw/chess/project/html/index.html">Startseite</a>';
navbarContent += '<a href="/DHBW-Projekt/Schachbrett">Spiel</a>';
navbarContent += '<a href="/WebApplication/dhbw/chess/project/html/highscore.html">Highscore</a>';
navbarContent += '<a href="/WebApplication/dhbw/chess/project/html/manual.html">Anleitung</a>';
navbarContent += '<a href="/WebApplication/dhbw/chess/project/html/impressum.html">Impressum</a>';
document.getElementById("navbar").innerHTML = navbarContent;
// bei der Anleitung vll noch ne hidden list drunter legen und da dann links rein packen, die dann weiterleiten zuden einzelnen unterpunkten des Textes, nachdem der formatiert worden ist und nicht mehr nur sporadisch eingefügt ist