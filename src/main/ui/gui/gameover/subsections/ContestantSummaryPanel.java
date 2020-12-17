package ui.gui.gameover.subsections;

import model.Contestant;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

// Represents a panel which displays the contestant's game stats
public class ContestantSummaryPanel extends JPanel {

    private static final Font NAME_FONT = new Font("", Font.BOLD, 20);
    private static final Font STATS_FONT = new Font("", Font.PLAIN, 16);

    private Contestant contestant;

    // EFFECTS: constructs a panel which displays the contestant's game stats
    public ContestantSummaryPanel(Contestant contestant) {
        this.contestant = contestant;
        initializeGraphics();
        displayName();
        displayStats();
    }

    // MODIFIES: this
    // EFFECTS: initializes graphics for this
    private void initializeGraphics() {
        setLayout(new GridLayout(2, 1));
    }

    // MODIFIES: this
    // EFFECTS: displays the contestant's name
    private void displayName() {
        JLabel name = new JLabel(contestant.getName());
        name.setFont(NAME_FONT);
        name.setHorizontalAlignment(JLabel.CENTER);

        add(name);
    }

    // MODIFIES: this
    // EFFECTS: displays the contestant's stats (earnings, questions answered correctly,
    //          favorite category)
    private void displayStats() {
        StyledDocument document = styleDocument();
        JTextPane stats = new JTextPane(document);

        stats.setFont(STATS_FONT);
        stats.setBackground(this.getBackground());
        stats.setEditable(false);
        add(stats);
    }

    // EFFECTS: creates a styled document which is center-aligned
    private StyledDocument styleDocument() {
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        String contents = "Earnings: " + formatEarnings(contestant.getEarnings())
                          + "\nCorrectness: " + calculateCorrectness()
                          + "\nFavorite Category: " + calculateFavoriteCategory();

        try {
            document.insertString(document.getLength(), contents, style);
        } catch (BadLocationException e) {
            diplayErrorMessage();
            System.exit(0);
        }

        return document;
    }

    // EFFECTS: notifies user that an error has occurred when trying to load the final
    //          standings for the game
    private void diplayErrorMessage() {
        String message = "A problem was encountered when trying to load the final standings."
                + "\nThe program will terminate.";
        JOptionPane.showMessageDialog(this, message, "Error: Unable to Load Final Standings",
                                        JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: formats the earnings as a string and adds proper numerical formatting for money
    private String formatEarnings(int e) {
        if (e < 0) {
            return "-$" + Integer.toString(e).replaceAll("-", "");
        }
        return "$" + e;
    }

    // EFFECTS: returns the contestant's percentage of questions answered correctly
    private String calculateCorrectness() {
        double percentageCorrect = contestant.calculateCorrectness();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return "%" + numberFormat.format(percentageCorrect);
    }

    // EFFECTS: returns the contestant's favorite category
    private String calculateFavoriteCategory() {
        Map<String, Integer> favoriteCategory = contestant.favoriteCategory();
        for (String key : favoriteCategory.keySet()) {
            int numTimesPicked = favoriteCategory.get(key);
            if (key == "None") {
                return "did not pick any categories";
            } else {
                if (numTimesPicked == 1) {
                    return key + " (picked " + numTimesPicked + " time)";
                }
                return key + " (picked " + numTimesPicked + " times)";
            }
        }
        return "did not pick any categories";
    }
}
