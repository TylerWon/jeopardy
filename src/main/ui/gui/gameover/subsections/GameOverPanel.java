package ui.gui.gameover.subsections;

import model.Contestant;
import model.Game;

import javax.swing.*;
import java.awt.*;

// Represents a panel which displays the final game standings
public class GameOverPanel extends JPanel {

    private static final Font WINNER_FONT = new Font("", Font.BOLD, 30);
    private static final Font THANK_YOU_FONT = new Font("", Font.PLAIN, 20);

    private JPanel centerPanel;

    private Game game;

    // EFFECTS: constructs a new panel which displays the final game standings
    public GameOverPanel(Game game) {
        this.game = game;
        initializeGraphics();
        displayWinner();
        addCenterPanel();
        displayThankYouMessage();
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: displays the winner of the game
    private void displayWinner() {
        Contestant winner = getWinner();

        JLabel winnerLabel = new JLabel("WINNER: " + winner.getName());
        winnerLabel.setFont(WINNER_FONT);
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(winnerLabel, BorderLayout.NORTH);
    }

    // EFFECTS: returns the winner from the game
    private Contestant getWinner() {
        Contestant winner = null;
        int counter = 0;

        for (Contestant c : game.getContestants()) {
            if (counter == 0) {
                winner = c;
            } else {
                if (c.getEarnings() > winner.getEarnings()) {
                    winner = c;
                }
            }
        }

        return winner;
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the CENTER of this
    private void addCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, game.getContestants().size()));
        add(centerPanel, BorderLayout.CENTER);

        addContestantSummaries();
    }

    // MODIFIES: this
    // EFFECTS: adds a summary of each contestant's game stats
    private void addContestantSummaries() {
        for (Contestant c : game.getContestants()) {
            ContestantSummaryPanel contestantSummaryPanel = new ContestantSummaryPanel(c);
            centerPanel.add(contestantSummaryPanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a thank you message to the user
    private void displayThankYouMessage() {
        JLabel thankYouMessage = new JLabel("Thanks for playing!");
        thankYouMessage.setFont(THANK_YOU_FONT);
        thankYouMessage.setHorizontalAlignment(JLabel.CENTER);
        add(thankYouMessage, BorderLayout.SOUTH);
    }
}
