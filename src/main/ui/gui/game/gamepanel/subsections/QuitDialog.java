package ui.gui.game.gamepanel.subsections;

import model.Game;
import persistence.JsonWriter;
import ui.gui.game.gamepanel.GamePanel;

import javax.swing.*;
import java.io.FileNotFoundException;

// Represents a dialog which asks user if they want to save before quitting
public class QuitDialog {

    private static final String DATA_STORE = "./data/savedGame.json";

    private GamePanel container;

    private JsonWriter jsonWriter;

    // EFFECTS: constructs a dialog which asks the user if they want to save before quitting
    public QuitDialog(GamePanel container, Game game) {
        initializeFields(container, game);
        displayDialog();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(GamePanel container, Game game) {
        this.container = container;
        jsonWriter = new JsonWriter(DATA_STORE);
    }

    // EFFECTS: displays the dialog
    private void displayDialog() {
        int result = JOptionPane.showConfirmDialog(container, "Do you want to save before quitting?",
                                                  "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

        processResult(result);
    }

    // EFFECTS: if result == 0 then save game, otherwise do not save game
    private void processResult(int result) {
        if (result == 0) {
            saveGame();
        } else if (result == 1) {
            displayGoodbyeMessage("Game was not saved. Thanks for playing!");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(container.getGame());
            jsonWriter.close();
            displayGoodbyeMessage("Game saved. Thanks for playing!");
        } catch (FileNotFoundException e) {
            displayGoodbyeMessage("A problem was encountered when trying to save the game. "
                                    + "The game will not be saved.\n Thanks for playing!");
        }
    }

    // EFFECTS: displays a good bye message
    private void displayGoodbyeMessage(String message) {
        JOptionPane.showMessageDialog(container, message, "", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }
}
