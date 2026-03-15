package controller;
import model.Direction;
import model.World;
import view.View;

/**
 * Our controller listens for key events on the main window.
 */

public class Controller {

    /** The world that is updated upon every key press. */
    private World world;
    private final View view;
    private int chaserCount;

    /**
	 * Creates a new instance.
	 * 
	 * @param world the world to be updated whenever the player should move.
	 * @param caged the {@link GraphicsProgram} we want to listen for key presses
	 *              on.
	 */
    public Controller(View view) { // Initaliziere Controller mit View
        this.chaserCount = 3; // Startwert für Chaser
        this.world = new World(chaserCount); // Erstelle neues World-Objekt mit Chaser-Anzahl
        this.view = view; 
        view.update(world); // Aktualisiere die View mit dem aktuellen World-Objekt
    }

    public void handleInput(Direction dir) { // Handle den input des Spielers
        if (world.isGameWon() || world.isGameLost()) 
            return;
        world.movePlayer(dir); 
        view.update(world);
    }

    public void restart(int newChaserCount) { // Starte das Spiel neu mit einer neuen Anzahl an Chasern
        this.chaserCount = newChaserCount;
        this.world = new World(chaserCount);
        view.update(world);
    }
}