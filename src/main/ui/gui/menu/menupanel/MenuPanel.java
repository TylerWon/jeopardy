package ui.gui.menu.menupanel;

import model.Game;
import persistence.JsonReader;
import persistence.Reader;
import ui.gui.game.GameFrame;
import ui.gui.menu.menupanel.subsections.ControlsPanel;
import ui.gui.menu.menupanel.subsections.newgame.NewGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents a panel displaying the opening menu options
public class MenuPanel extends JPanel {

    private static final Font BUTTON_FONT = new Font("", Font.BOLD, 12);
    private static final Font TITLE_FONT = new Font("", Font.PLAIN, 70);
    private static final String DATA_STORE = "./data/savedGame.json";

    private JPanel centerPanel;
    private JButton newGame;
    private JButton loadGame;
    private JButton controls;
    private JButton quit;
    private JFrame frame;

    private Reader jsonReader;

    // EFFECTS: constructs a panel which displays the menu
    public MenuPanel(JFrame frame) {
        super();
        initializeFields(frame);
        initializeGraphics();
        addTitle();
        addCenterPanel();
        addCredits();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(JFrame frame) {
        this.frame = frame;
        this.jsonReader = new JsonReader(DATA_STORE);
    }

    // MODIFIES: this
    // EFFECTS: renders the graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout(0, 45));
    }

    // MODIFIES: this
    // EFFECTS: adds title label to this
    private void addTitle() {
        JLabel title = new JLabel("JEOPARDY!");
        title.setFont(TITLE_FONT);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: add new panel to the CENTER of this which contains menu buttons
    private void addCenterPanel() {
        centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        addNewGameButton();
        addLoadGameButton();
        addControlsButton();
        addQuitButton();
    }

    // MODIFIES: this
    // EFFECTS: adds a new game button to panel
    private void addNewGameButton() {
        newGame = new JButton("New Game");
        initializeButton(newGame);
        centerPanel.add(newGame);
    }

    // MODIFIES: this
    // EFFECTS: adds a load game button to panel
    private void addLoadGameButton() {
        loadGame = new JButton("Load Game");
        initializeButton(loadGame);
        centerPanel.add(loadGame);
    }

    // MODIFIES: this
    // EFFECTS: adds a controls button to panel
    private void addControlsButton() {
        controls = new JButton("Controls");
        initializeButton(controls);
        centerPanel.add(controls);
    }

    // MODIFIES: this
    // EFFECTS: adds a quit button to panel
    private void addQuitButton() {
        quit = new JButton("Quit");
        initializeButton(quit);
        centerPanel.add(quit);
    }

    // MODIFIES: button
    // EFFECTS:  initializes properties of button
    private void initializeButton(JButton button) {
        button.setFocusable(false);
        button.setFont(BUTTON_FONT);
        button.addActionListener(new MenuOptionsListener());
    }

    // MODIFIES: this
    // EFFECTS: adds credits to this
    private void addCredits() {
        JLabel credits = new JLabel("by Tyler Won");
        credits.setHorizontalAlignment(JLabel.RIGHT);
        add(credits, BorderLayout.SOUTH);
    }

    // Represents an ActionListener which listens for when any of the menu buttons are pressed
    private class MenuOptionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGame) {
                MenuPanel.this.setVisible(false);
                frame.add(new NewGamePanel(frame));
            } else if (e.getSource() == loadGame) {
                loadGame();
            } else if (e.getSource() == controls) {
                MenuPanel.this.setVisible(false);
                frame.add(new ControlsPanel(MenuPanel.this));
            } else if (e.getSource() == quit) {
                System.exit(0);
            }
        }

        // MODIFIES: this
        // EFFECTS: loads game from saved file
        private void loadGame() {
            try {
                Game game = jsonReader.read();
                JOptionPane.showMessageDialog(MenuPanel.this, "Game successfully loaded",
                                              "", JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                new GameFrame(game);
            } catch (IOException exp) {
                JOptionPane.showMessageDialog(MenuPanel.this, "Unable to load game.",
                                              "", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
