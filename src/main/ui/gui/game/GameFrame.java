package ui.gui.game;

import model.Game;
import ui.gui.game.gamepanel.GamePanel;

import javax.swing.*;
import java.awt.*;

// Represents the window in which the game is displayed
public class GameFrame extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    private Game game;

    // EFFECTS: constructs a frame for the game
    public GameFrame(Game game) {
        super("Jeopardy");
        this.game = game;
        initializeGraphics();
    }

    // EFFECTS: draws the JFrame window where this MenuFrame app will operate
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        addGamePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add menu panel to this
    private void addGamePanel() {
        add(new GamePanel(this, game));
    }
}
