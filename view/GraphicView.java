package view;
import model.CellType;
import model.Chaser;
import model.World;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Stellt die grafische Ansicht der Welt dar.
 * Zeichnet das Labyrinth, den Spieler und die Verfolger auf ein JPanel.
 */
public class GraphicView extends JPanel implements View {
    /** Referenz auf die aktuelle Welt */
    private World world;
    /** Größe eines Feldes in Pixeln */
    private final int tileSize = 40;

    /**
     * Aktualisiert die Ansicht mit einer neuen Welt und löst das Neuzeichnen aus.
     * @param world Die aktuelle Welt
     */
    @Override
    public void update(World world) {
        this.world = world;
        repaint(); // fordert Swing auf, die Komponente neu zu zeichnen
    }

    /**
     * Zeichnet das Labyrinth, den Spieler, die Verfolger und ggf. Statusmeldungen.
     * Wird von Swing automatisch aufgerufen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (world == null) return; // Nichts zeichnen, wenn keine Welt vorhanden

        // Zeichnet jedes Feld entsprechend seinem Typ
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                CellType type = world.getCellType(x, y);
                switch (type) {
                    case WALL:
                        {
                        Random rand = new Random();
                        int red = 180 + rand.nextInt(75); // Sattes Rot bis fast maximal
                        int green = 20 + rand.nextInt(30); // Wenig Grün für Lava-Effekt
                        int blue = 0 + rand.nextInt(20);   // Kaum Blau
                        g.setColor(new Color(red, green, blue)); // Lava-Rot für Wände
                        }
                        break;
                    case PATH:
                    {
                    // Verschiedene dunkle Violett-/Blau-Töne für Obsidian-Effekt
                        int base = 30 + (x * y) % 30; // Basiswert für Variation
                        int r = base;
                        int gCol = base;
                        int b = 60 + (x + y) % 80; // Blauanteil etwas höher
                    g.setColor(new Color(r, gCol, b)); // Obsidianfarben für Pfade
                        }
                        break;
                    case START:
                        g.setColor(Color.BLUE);        // Startfeld blau
                        break;
                    case GOAL:
                        g.setColor(Color.YELLOW);       // Zielfeld gelb
                        break;
                }
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        // Zeichnet den Spieler als schwarzen Kreis
        g.setColor(Color.PINK);
        g.fillOval(world.getPlayerX() * tileSize, world.getPlayerY() * tileSize, tileSize, tileSize);

        // Zeichnet alle Verfolger als rote Kreise
        g.setColor(Color.BLACK);
        for (Chaser ch : world.getChasers()) {
            g.fillOval(ch.getX() * tileSize, ch.getY() * tileSize, tileSize, tileSize);
        }

        // Zeigt Statusmeldungen bei Gewinn oder Niederlage an
        if (world.isGameWon()) drawCenteredText(g, "Gewonnen!", Color.BLUE);
        if (world.isGameLost()) drawCenteredText(g, "Verloren!", Color.RED);
    }

    /**
     * Zeichnet einen Text zentriert am unteren Rand des Panels.
     * @param g Grafik-Kontext
     * @param text Anzuzeigender Text
     * @param color Textfarbe 
     */
    private void drawCenteredText(Graphics g, String text, Color color) {
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight() - 20;
        g.drawString(text, x, y);
    }

    /**
     * Gibt die bevorzugte Größe des Panels an, abhängig von der Weltgröße.
     * Wird von Swing für das Layout verwendet.
     */
    @Override
    public Dimension getPreferredSize() {
        if (world != null) {
            return new Dimension(tileSize * world.getWidth(), tileSize * world.getHeight());
        } else {
            return new Dimension(tileSize * 10, tileSize * 10);
        }
    }

}