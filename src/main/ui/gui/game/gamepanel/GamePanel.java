package ui.gui.game.gamepanel;

import model.Category;
import model.Contestant;
import model.Game;
import ui.gui.game.GameFrame;
import ui.gui.game.gamepanel.subsections.ContestantInfoPanel;
import ui.gui.game.gamepanel.subsections.QuitDialog;
import ui.gui.game.gamepanel.subsections.categorypanel.CategoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Represents a panel displaying the game
public class GamePanel extends JPanel {

    private static final Font BUTTON_FONT = new Font("", Font.BOLD, 12);

    private JButton quit;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private GameFrame frame;

    private Game game;
    private Contestant picker;
    private Random random;

    // EFFECTS: constructs a panel which displays the game
    public GamePanel(GameFrame frame, Game game) {
        super();
        initializeFields(frame, game);
        initializeGraphics();
        pickNotification();
        addNorthPanel();
        addCenterPanel();
        addSouthPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(GameFrame frame, Game game) {
        this.frame = frame;
        this.game = game;
        random = new Random();
        picker = selectFirstContestant();

    }

    // EFFECTS: randomly selects the first contestant to pick a category
    private Contestant selectFirstContestant() {
        int randomIndex = random.nextInt(game.numberOfContestants());
        return game.getContestant(randomIndex);
    }

    // MODIFIES: this
    // EFFECTS: renders graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout(0, 15));
    }

    // EFFECTS: notifies the user of which contestant gets to pick a category first
    private void pickNotification() {
        String message = picker.getName() + ", it is your pick!";
        JOptionPane.showMessageDialog(this, message, "", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to NORTH of this which contains the quit button
    private void addNorthPanel() {
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);

        addQuitButton();
    }

    // MODIFIES: this
    // EFFECTS: adds quit button to this
    private void addQuitButton() {
        quit = new JButton("Quit");
        quit.setFocusable(false);
        quit.setFont(BUTTON_FONT);
        quit.addActionListener(new GamePanel.QuitButtonListener());
        northPanel.add(quit, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the CENTER of this which contains categories/questions
    private void addCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 5));
        add(centerPanel, BorderLayout.CENTER);

        addGameboard();
    }

    // MODIFIES: this
    // EFFECTS: adds the game board (categories and questions) to the center panel
    private void addGameboard() {
        for (Category c : game.getCategories()) {
            CategoryPanel categoryPanel = new CategoryPanel(this, c);
            centerPanel.add(categoryPanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the SOUTH of this which contains contestant info
    private void addSouthPanel() {
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout());
        add(southPanel, BorderLayout.SOUTH);

        addContestantInfo();
    }

    // MODIFIES: this
    // EFFECTS: add contestant info to the south panel, highlights the name of the contestant who is picking
    private void addContestantInfo() {
        for (Contestant c : game.getContestants()) {
            ContestantInfoPanel contestantInfoPanel = new ContestantInfoPanel(this, c);
            southPanel.add(contestantInfoPanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the picker and contestants panel
    public void update(Contestant picker) {
        this.picker = picker;
        remove(southPanel);
        addSouthPanel();
    }

    // getters
    public JFrame getFrame() {
        return frame;
    }

    public Contestant getPicker() {
        return picker;
    }

    public Game getGame() {
        return game;
    }

    // Represents an ActionListener which listens for when the quit button is pressed
    private class QuitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == quit) {
                new QuitDialog(GamePanel.this, game);
            }
        }
    }
}
