package model;
import java.util.*;

/**
 * Repräsentiert einen Verfolger (Chaser), der sich im Labyrinth auf den Spieler zubewegt.
 * Nutzt den Breadth-First Search Algorithmus und vermeidet zuletzt besuchte Felder.
 */
public class Chaser {
    private int x, y; // aktuelle Position des Verfolgers
    private final Deque<String> recentPositions = new ArrayDeque<>(); // speichert letzte Positionen zur Vermeidung von Schleifen
    private static final int MEMORY_SIZE = 5; // wie viele Positionen gemerkt werden

    /**
     * Erstellt einen Verfolger an gegebener Startposition.
     */
    public Chaser(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Bewegt den Verfolger in Richtung Ziel (Spieler).
     * Nutzt eine Breitensuche (BFS) für den kürzesten Weg.
     * Falls kein Weg gefunden wird, wird zufällig in eine mögliche Richtung gegangen.
     * Zuletzt besuchte Felder werden gemieden.
     */
    public void moveTowards(int targetX, int targetY, World world) {
        int V = world.getWidth() * world.getHeight(); // Gesamtzahl der Felder
        boolean[] visited = new boolean[V]; // merkt besuchte Felder
        Map<Integer, Integer> cameFrom = new HashMap<>(); // merkt Vorgänger für Pfadrekonstruktion
        Queue<Integer> queue = new LinkedList<>(); // Warteschlange für BFS

        int startIdx = y * world.getWidth() + x; // Startindex im Array
        int goalIdx = targetY * world.getWidth() + targetX; // Zielindex

        visited[startIdx] = true;
        queue.add(startIdx);
        boolean found = false;

        // BFS: Sucht kürzesten Weg zum Ziel
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int cx = current % world.getWidth();
            int cy = current / world.getWidth();

            if (current == goalIdx) {
                found = true;
                break;
            }

            // Prüft alle Nachbarn (oben, unten, links, rechts)
            for (Direction dir : Direction.values()) {
                int nx = cx + dir.deltaX;
                int ny = cy + dir.deltaY;
                int neighborIdx = ny * world.getWidth() + nx;

                // Nur gültige, begehbare und noch nicht besuchte Felder
                if (world.inBounds(nx, ny) && world.isWalkable(nx, ny) && !visited[neighborIdx]) {
                    visited[neighborIdx] = true;
                    queue.add(neighborIdx);
                    cameFrom.put(neighborIdx, current);
                }
            }
        }

        // Falls ein Weg gefunden wurde, gehe einen Schritt in Richtung Ziel
        if (found) {
            int current = goalIdx;
            while (cameFrom.containsKey(current) && cameFrom.get(current) != startIdx) {
                current = cameFrom.get(current);
            }
            int nextX = current % world.getWidth();
            int nextY = current / world.getWidth();
            x = nextX;
            y = nextY;
        } else {
            // Kein Weg gefunden: Zufällige Bewegung, aber keine Wiederholung der letzten Felder
            List<Direction> dirs = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
            Collections.shuffle(dirs);
            for (Direction dir : dirs) {
                int nx = x + dir.deltaX;
                int ny = y + dir.deltaY;
                String key = nx + "," + ny;
                if (world.isWalkable(nx, ny) && !recentPositions.contains(key)) {
                    x = nx;
                    y = ny;
                    break;
                }
            }
        }

        // Aktuelle Position merken, um Schleifen zu vermeiden
        String currentKey = x + "," + y;
        recentPositions.addLast(currentKey);
        if (recentPositions.size() > MEMORY_SIZE) {
            recentPositions.removeFirst();
        }
    }

    /** Gibt die aktuelle X-Position zurück */
    public int getX() { 
        return x; 
    }

    /** Gibt die aktuelle Y-Position zurück */
    public int getY() { 
        return y; 
    }
}