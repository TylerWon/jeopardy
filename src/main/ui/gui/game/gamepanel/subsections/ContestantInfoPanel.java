package ui.gui.game.gamepanel.subsections;

import model.Contestant;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

// Represents a panel which displays the contestant name and earnings
public class ContestantInfoPanel extends JPanel {

    private static final Font CONTESTANT_FONT = new Font("", Font.PLAIN, 18);

    private JLabel name;
    private JLabel earnings;
    private JPanel container;

    private Contestant contestantToAdd;
    private Contestant picker;

    // EFFECTS: constructs a new panel which displays the contestant name and earnings
    public ContestantInfoPanel(JPanel container, Contestant contestantToAdd, Contestant picker) {
        initializeFields(container, contestantToAdd, picker);
        initializeGraphics();
        addName();
        addEarnings();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(JPanel container, Contestant contestantToAdd, Contestant picker) {
        this.container = container;
        this.contestantToAdd = contestantToAdd;
        this.picker = picker;
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics for this
    private void initializeGraphics() {
        setLayout(new GridLayout(2, 1));
        setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
    }

    // MODIFIES: this
    // EFFECTS: adds the contestant's name to this
    private void addName() {
        name = new JLabel(contestantToAdd.getName());
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setFont(CONTESTANT_FONT);

        if (contestantToAdd.equals(picker)) {
            name.setFont(new Font("", Font.BOLD, 16));
        }

        add(name);
    }

    // MODIFIES: this
    // EFFECTS: adds the contestant's earnings to this
    private void addEarnings() {
        int e = contestantToAdd.getEarnings();
        String stringE = formatEarnings(e);

        earnings = new JLabel(stringE);
        earnings.setHorizontalAlignment(JLabel.CENTER);
        earnings.setFont(CONTESTANT_FONT);
        add(earnings);
    }

    // EFFECTS: converts e to a string and adds proper numerical formatting to display money
    private String formatEarnings(int e) {
        if (e < 0) {
            return "-$" + Integer.toString(e).replaceAll("-", "");
        }
        return "$" + e;
    }
}
