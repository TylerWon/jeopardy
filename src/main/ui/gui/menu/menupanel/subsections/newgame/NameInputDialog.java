package ui.gui.menu.menupanel.subsections.newgame;

import model.Contestant;
import model.Game;
import ui.gui.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Represents a dialog which allows user to input the name for each contestant in the game
public class NameInputDialog {

    private JPanel dialogPanel;
    private JPanel centerDialogPanel;
    private List<JTextField> textFields;
    private JPanel container;
    private JFrame frame;

    private int numberOfPlayers;
    private Game game;

    // EFFECTS: construct a dialog in which the user can input the name for each contestant in the game
    public NameInputDialog(JFrame frame, JPanel container, Game game, int numberOfPlayers) {
        initializeFields(frame, container, game, numberOfPlayers);
        displayDialog();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(JFrame frame, JPanel container, Game game, int numberOfPlayers) {
        this.frame = frame;
        this.container = container;
        textFields = new ArrayList<>();
        this.game = game;
        this.numberOfPlayers = numberOfPlayers;
    }

    // MODIFIES: this
    // EFFECTS: displays the dialog
    private void displayDialog() {
        createDialogPanel();
        int result = JOptionPane.showOptionDialog(container,
                                                 dialogPanel,
                                                 null,
                                                 JOptionPane.DEFAULT_OPTION,
                                                 JOptionPane.PLAIN_MESSAGE,
                                                 null,
                                                 null,
                                                 0);
        processResult(result);
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel and adds it to dialog
    private void createDialogPanel() {
        initializeDialogPanel();
        addTitle();
        addCenterDialogPanel();
    }

    // MODIFIES: this
    // EFFECTS: render graphics for panel
    private void initializeDialogPanel() {
        dialogPanel = new JPanel();
        dialogPanel.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: adds title to dialog panel
    private void addTitle() {
        JLabel title = new JLabel("Enter contestants names:");
        title.setHorizontalAlignment(JLabel.LEFT);
        dialogPanel.add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the CENTER of the dialog panel
    private void addCenterDialogPanel() {
        centerDialogPanel = new JPanel();
        centerDialogPanel.setLayout(new GridLayout(0, 1));
        dialogPanel.add(centerDialogPanel, BorderLayout.CENTER);

        addTextFields();
    }

    // MODIFIES: this
    // EFFECTS: add text fields to dialog panel
    private void addTextFields() {
        for (int i = 1; i <= numberOfPlayers; i++) {
            JTextField textField = new JTextField("Contestant " + i);
            centerDialogPanel.add(textField);
            textFields.add(textField);
        }
    }

    // MODIFIES: this
    // EFFECTS: if result == 0 then create new contestants
    //          otherwise terminate program
    private void processResult(int result) {
        if (result == 0) {
            for (JTextField input : textFields) {
                game.addContestant(new Contestant(input.getText()));
            }

            frame.dispose();
            new GameFrame(game);
        }
    }
}
