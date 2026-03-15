package view;
import model.World;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {
    @Override
    public void update(World world) { // Aktualisiert die Konsole mit dem aktuellen Zustand der Welt
        for (int y = 0; y < world.getHeight(); y++) { // Iteriert über die Höhe der Welt
            final int finalY = y;
            for (int x = 0; x < world.getWidth(); x++) { // Iteriert über die Breite der Welt
                final int finalX = x;
                if (world.getPlayerX() == finalX && world.getPlayerY() == finalY) {
                    System.out.print("P ");
                } else if (world.getChasers().stream().anyMatch(c -> c.getX() == finalX && c.getY() == finalY)) { 
                    System.out.print("X ");
                } else {
                    switch (world.getCellType(finalX, finalY)) {
                        case WALL:
                            System.out.print("# ");
                            break;
                        case PATH:
                            System.out.print("  ");
                            break;
                        case START:
                            System.out.print("S ");
                            break;
                        case GOAL:
                            System.out.print("Z ");
                            break;
                    }
                }
            }
            // A newline after every row 
            System.out.println();
        }
        if (world.isGameWon()) System.out.println("Gewonnen!"); // Ausgabe bei Gewinn
        if (world.isGameLost()) System.out.println("Verloren!"); // Ausgabe bei Niederlage
    }
}