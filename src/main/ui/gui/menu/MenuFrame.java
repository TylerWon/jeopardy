package ui.gui.menu;

import ui.gui.menu.menupanel.MenuPanel;

import javax.swing.*;
import java.awt.*;

// Represents the window in which the opening menu is displayed
public class MenuFrame extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    // EFFECTS: constructs a frame for the menu
    public MenuFrame() {
        super("Jeopardy!");
        initializeGraphics();
    }

    // EFFECTS: draws the JFrame window where this MenuFrame app will operate
    private void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        addMenuPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add menu panel to this
    private void addMenuPanel() {
        add(new MenuPanel(this));
    }
}
