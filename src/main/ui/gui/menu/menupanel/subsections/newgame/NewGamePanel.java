package ui.gui.menu.menupanel.subsections.newgame;

import model.Game;
import persistence.QuestionsReader;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents a panel displaying options to start a new game
public class NewGamePanel extends JPanel {

    private static final Font TITLE_FONT = new Font("", Font.PLAIN, 35);
    private static final Font BUTTON_FONT = new Font("", Font.BOLD, 12);
    private static final String QUESTIONS_STORE = "./data/questions.json";

    private JPanel centerPanel;
    private JButton twoPlayers;
    private JButton threePlayers;
    private JButton fourPlayers;
    private JFrame frame;

    private Reader questionsReader;

    // EFFECTS: constructs a new game panel
    public NewGamePanel(JFrame frame) {
        super();
        initializeFields(frame);
        initializeGraphics();
        addTitle();
        addCenterPanel();
    }

    // MODIFIES: this
    // EFFECTS: renders graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout(0, 80));
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void initializeFields(JFrame frame) {
        this.frame = frame;
        questionsReader = new QuestionsReader(QUESTIONS_STORE);
    }

    // MODIFIES: this
    // EFFECTS: adds a title to this
    private void addTitle() {
        JLabel title = new JLabel("How many contestants?");
        title.setFont(TITLE_FONT);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: add a new panel to the CENTER of this which contains number of players buttons
    private void addCenterPanel() {
        centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        addTwoPlayersButton();
        addThreePlayersButton();
        addFourPlayersButton();
    }

    // MODIFIES: this
    // EFFECTS: adds two players button to panel
    private void addTwoPlayersButton() {
        twoPlayers = new JButton("2");
        initializeButton(twoPlayers);
        twoPlayers.addActionListener(new NumberOfContestantsListener());
        centerPanel.add(twoPlayers);
    }

    // MODIFIES: this
    // EFFECTS: adds three players button to panel
    private void addThreePlayersButton() {
        threePlayers = new JButton("3");
        initializeButton(threePlayers);
        threePlayers.addActionListener(new NumberOfContestantsListener());
        centerPanel.add(threePlayers);
    }

    // MODIFIES: this
    // EFFECTS: adds adds four players button to panel
    private void addFourPlayersButton() {
        fourPlayers = new JButton("4");
        initializeButton(fourPlayers);
        fourPlayers.addActionListener(new NumberOfContestantsListener());
        centerPanel.add(fourPlayers);
    }

    // MODIFIES: button
    // EFFECTS: renders graphics for button
    private void initializeButton(JButton button) {
        button.setFocusable(false);
        button.setFont(BUTTON_FONT);
    }

    // Represents an ActionListener which listens to buttons which represent the number of contestants
    // a user wants in their game
    private class NumberOfContestantsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == twoPlayers) {
                createNewGame(2);
            } else if (e.getSource() == threePlayers) {
                createNewGame(3);
            } else if (e.getSource() == fourPlayers) {
                createNewGame(4);
            }
        }

        // MODIFIES: NewGamePanel
        // EFFECTS: create a new game with numberOfPlayers
        private void createNewGame(int numberOfPlayers) {
            try {
                Game game = questionsReader.read();
                new NameInputDialog(frame, NewGamePanel.this, game, numberOfPlayers);
            } catch (IOException ioException) {
                String message = "A problem was encountered when trying to create the game."
                                       + "\nThe program will terminate.";
                JOptionPane.showMessageDialog(NewGamePanel.this, message,
                                              "Error: Unable to Create Game", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}
