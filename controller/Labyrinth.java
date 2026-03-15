package controller;
import model.Direction;
import view.GraphicView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Startpunkt der Anwendung. Initialisiert das Hauptfenster,
 * die grafische Ansicht und die Steuerung.
 */
public class Labyrinth {
    public static void main(String[] args) {
        // Startet die GUI im Event-Dispatch-Thread (empfohlen für Swing)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Hauptfenster erstellen
                JFrame frame = new JFrame("Labyrinth Game");
                // Grafische Ansicht erzeugen
                GraphicView graphicView = new GraphicView();
                // Controller mit View verbinden
                Controller controller = new Controller(graphicView);

                // Panel für Steuerelemente (Slider und Button)
                JPanel controlPanel = new JPanel();
                // Slider zur Auswahl der Verfolger-Anzahl (1 bis 10, Startwert 3)
                JSlider chaserSlider = new JSlider(1, 10, 3);
                chaserSlider.setMajorTickSpacing(1);
                chaserSlider.setPaintLabels(true);
                chaserSlider.setPaintTicks(true);
                // Button zum Neustarten des Spiels
                JButton restartButton = new JButton("Neustart");

                // Aktion für den Neustart-Button: Spiel mit aktueller Verfolger-Anzahl neu starten
                restartButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.restart(chaserSlider.getValue());
                        frame.requestFocusInWindow(); // Fokus erneut setzen
                    }
                });

                // Steuerelemente zum Panel hinzufügen
                controlPanel.add(new JLabel("Verfolger:"));
                controlPanel.add(chaserSlider);
                controlPanel.add(restartButton);

                // KeyListener für Tastatursteuerung (Pfeiltasten)
                frame.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                controller.handleInput(Direction.UP);
                                break;
                            case KeyEvent.VK_DOWN:
                                controller.handleInput(Direction.DOWN);
                                break;
                            case KeyEvent.VK_LEFT:
                                controller.handleInput(Direction.LEFT);
                                break;
                            case KeyEvent.VK_RIGHT:
                                controller.handleInput(Direction.RIGHT);
                                break;
                        }
                    }
                });

                // Layout: Spielfeld in die Mitte, Steuerpanel nach unten
                frame.setLayout(new BorderLayout());
                frame.add(graphicView, BorderLayout.CENTER); // Benutzt die Java Klasse BorderLayout für die Anordnung "Spielfeld in der Mitte = Center"
                frame.add(controlPanel, BorderLayout.SOUTH); // Benutzt die Java Klasse BorderLayout für die Anordnung "Steuerpanel unten = South"
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setFocusable(true);
                frame.requestFocusInWindow(); // Initialen Fokus auf das geöffnete Fenster setzen
            }
        });
    }
}