package ui.gui.gameover;

import model.Game;
import ui.gui.gameover.subsections.GameOverPanel;

import javax.swing.*;
import java.awt.*;

// Represents the window in which the final game standings are displayed
public class GameOverFrame extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    private Game game;

    // EFFECTS: constructs a new window which displays the final game standings
    public GameOverFrame(Game game) {
        this.game = game;
        initializeGraphics();
    }

    // EFFECTS: draws the JFrame window where this MenuFrame app will operate
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        addGameOverPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds game over panel to this
    private void addGameOverPanel() {
        add(new GameOverPanel(game));
    }
}
