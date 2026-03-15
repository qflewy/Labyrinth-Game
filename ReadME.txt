Disclaimer: Small and first University project from two students 

1. Technische Grundlagen
------------------------
Programmiersprache: Java 21 (openjdk version "24.0.1")
Entwicklungsumgebung: Visual Studio Code
Laufzeit: OpenJDK 64-Bit Server VM (build 24.0.1+9-30)


2. Spielstart und Steuerung:
----------------------------
- Starten Sie das Spiel über die Klasse Labyrinth.java.
- Die GUI öffnet sich automatisch.
- Gesteuert wird mit den Pfeiltasten:
↑ = Hoch, ↓ = Runter, ← = Links, → = Rechts.
- Ziel ist es, vom Startpunkt (oben links) zum Zielpunkt (unten rechts) zu gelangen und dabei nicht von einem Verfolger erwischt zu werden.
- Verfolger bewegen sich nach jedem Spielzug. Berührt ein Verfolger den Spieler, ist das Spiel verloren.
- Mit dem „Neustart“-Button kann das Spiel neu gestartet werden.
- Die Anzahl der Verfolger kann über einen Schieberegler eingestellt werden.


3. Eingebaute Features:
-----------------------
- Labyrinth wird zufällig generiert (30x50 Felder standardmäßig) - ist in der World.java Datei veränderbar
- Das Labyrinth hat das Thema: LAVA!, alla Super Mario vs. Bowser
- Startfeld, Zielfeld sind fest, Lava und begehbare Wege werden generiert
- Spielersteuerung über Tastatur
- Schwierigkeit über Anzahl der Verfolger einstellbar (Slider)
- 1. Verfolger: Spawnt oben rechts; 2. Oben rechts; Ab dem 3. ist der Startpunkt zufällig
- GUI-Element zum Neustart des Spiels
- Blockierte Felder (Lava) als Hindernisse für Spieler und Verfolger
- Verfolger starten an unterschiedlichen Positionen
- Die Verfolger wurden so Programmiert, dass sie per Breadth-First Search (5.1), da einfachere Methoden immer wieder dazu geführt haben, dass die Verfolger in einigen                      generierten Labyrinth-Mustern stecken bleiben.


4. Funktionsweise der GUI:
-------------------------
Die GUI besteht aus zwei Hauptkomponenten:
1. Spielfeldanzeige (GraphicView.java), welche das Labyrinth, Spieler, Ziel, Verfolger und Wände anzeigt.
2. Steuerpanel mit einem Slider um die Anzahl der Verfolger auszuwählen und einem Neustart-Button um ein neues Spiel zu starten.


5. Anmerkung:
-------------
Es könnte passieren, dass man keinen Zielführenden Weg hat, sondern man "eingesperrt" ist. Das könnte man mit einem weiteren Algorithmus, der garantiert das es eine Lösung gibt verbessern. Dieser Fall aber eher unwahrscheinlich und wir wissen nicht genau wie man es umsetzen sollte. Des weiteren felht leider die Zeit =/


5.1 Verfolger-Logik (Breadth-First Search):
-------------------------------------------
Die Verfolger im Spiel nutzen den Breadth-First Search (BFS)-Algorithmus, um den kürzesten Weg zum Spieler zu finden. Das Spielfeld wird als Graph behandelt, bei dem jedes Feld ein Knoten ist. BFS durchläuft diesen Graphen, um eine optimale Route zu ermitteln.

Falls der direkte Weg blockiert ist (z. B. durch Wände), wird zufällig eine benachbarte Position gewählt, die nicht kürzlich besucht wurde. So vermeiden die Verfolger es, sich in einer Sackgasse festzufahren oder im Kreis zu laufen.

Diese Logik sorgt dafür, dass die Verfolger realistisch und herausfordernd agieren, ohne völlig unberechenbar zu sein.

Quelle für Algorithmus-Implementierung: "https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/"

5.2 Verfolger:
--------------
Es ist für Verfolger möglich sich auf dem gleichen Feld zu befinden, als wir versucht haben dies zu verhindern hat es nur zu Fehlern geführt und wir betrachten es deshalb jetzt als feature. =)
