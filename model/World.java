package model;
import java.util.*;

public class World {

    /** Hier kann man, indem man die Zahl verändert, die Spielfeldgröße verändern. */

    /** Breite des Labyrinths */
    private final int width = 50;


    /** Höhe des Labyrinths */
    private final int height = 30;


    /** Spielfeld als 2D-Array */
    private final CellType[][] grid;
    /** Spielerposition X */
    private int playerX;
    /** Spielerposition Y */
    private int playerY;
    /** Zielposition X */
    private final int goalX;
    /** Zielposition Y */
    private final int goalY;
    /** Liste der Verfolger */
    private final List<Chaser> chasers = new ArrayList<>();
    /** Spiel gewonnen-Flag */
    private boolean gameWon = false;
    /** Spiel verloren-Flag */
    private boolean gameLost = false;

    /** Konstruktor: Initialisiert Welt und Verfolger */
    public World(int chaserCount) {
        grid = new CellType[width][height];
        generateLabyrinth();
        playerX = 0;
        playerY = 0;
        goalX = width - 1;
        goalY = height - 1;
        spawnChasers(chaserCount);
    }

    /** Erstellt zufälliges Labyrinth und setzt Start/Ziel */
    private void generateLabyrinth() {
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = (rand.nextDouble() < 0.2) ? CellType.WALL : CellType.PATH;
            }
        }
        grid[0][0] = CellType.START; // Startfeld
        grid[width - 1][height - 1] = CellType.GOAL; // Zielfeld

        // Mindestens ein Startweg muss frei sein
        if (inBounds(1, 0)) grid[1][0] = CellType.PATH;
        if (inBounds(0, 1)) grid[0][1] = CellType.PATH;
    }

    /** Prüft, ob Feld frei ist (kein Spieler, Ziel, Wand) */
    private boolean isFree(int x, int y) {
        return inBounds(x, y)
            && grid[x][y] != CellType.WALL
            && !(x == playerX && y == playerY)
            && !(x == goalX && y == goalY);
    }

    /** Platziert Verfolger an festen und zufälligen Positionen */
    public void spawnChasers(int count) {
        chasers.clear();
        Set<String> used = new HashSet<>();
        Random rand = new Random();

        // Feste Startpunkte für erste Verfolger
        if (count >= 1 && isFree(width - 1, 0)) {
            chasers.add(new Chaser(width - 1, 0));
            used.add((width - 1) + "," + 0);
        }
        if (count >= 2 && isFree(0, height - 1)) {
            chasers.add(new Chaser(0, height - 1));
            used.add("0," + (height - 1));
        }
        if (count >= 3 && isFree(width - 1, height - 1)) {
            chasers.add(new Chaser(width - 1, height - 1));
            used.add((width - 1) + "," + (height - 1));
        }

        // Weitere Verfolger zufällig platzieren
        int attempts = 0;
        for (int i = chasers.size(); i < count && attempts < 1000; attempts++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            String key = x + "," + y;
            if (isFree(x, y) && !used.contains(key)) {
                chasers.add(new Chaser(x, y));
                used.add(key);
                i++;
            }
        }
    }

    /** Bewegt den Spieler und prüft auf Gewinn/Niederlage */
    public void movePlayer(Direction dir) {
        if (gameWon || gameLost) return;

        int newX = playerX + dir.deltaX;
        int newY = playerY + dir.deltaY;

        if (isWalkable(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }

        // Gewinnbedingung prüfen
        if (playerX == goalX && playerY == goalY) {
            gameWon = true;
            return;
        }

        // Verfolger bewegen und auf Kollision prüfen
        for (Chaser ch : chasers) ch.moveTowards(playerX, playerY, this);
        for (Chaser ch : chasers) {
            if (ch.getX() == playerX && ch.getY() == playerY) gameLost = true;
        }
    }

    /** Prüft, ob Feld begehbar ist */
    public boolean isWalkable(int x, int y) {
        return inBounds(x, y) && grid[x][y] != CellType.WALL;
    }

    /** Prüft, ob Koordinaten im Spielfeld liegen */
    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // Getter-Methoden für Spielfeld und Status
    public int getPlayerX() { 
        return playerX; 
    }

    public int getPlayerY() { 
        return playerY; 
    }

    public CellType getCellType(int x, int y) { 
        return grid[x][y]; 
    }

    public List<Chaser> getChasers() { 
        return chasers; 
    }

    public boolean isGameWon() { 
        return gameWon; 
    }

    public boolean isGameLost() { 
        return gameLost; 
    }

    public int getWidth() { 
        return width; 
    }

    public int getHeight() { 
        return height; 
    }

    public int getGoalX() { 
        return goalX; 
    }

    public int getGoalY() { 
        return goalY; 
    }
}