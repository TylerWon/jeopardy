package ui.gui.game.gamepanel.subsections.categorypanel;

import model.Category;
import model.Question;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

// Represents a dialog which displays the answered questions for a category
public class CategoryInfoDialog {

    private static final Font TITLE_FONT = new Font("", Font.BOLD, 16);

    private JPanel dialogPanel;
    private JPanel centerDialogPanel;
    private JPanel parentContainer;

    private Category category;

    // EFFECTS: constructs a dialog which displays the answered questions for category
    public CategoryInfoDialog(JPanel parentContainer, Category category) {
        initializeFields(parentContainer, category);
        displayDialog();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(JPanel parentContainer, Category category) {
        this.parentContainer = parentContainer;
        this.category = category;
    }

    // MODIFIES: this
    // EFFECTS: displays the dialog
    private void displayDialog() {
        createDialogPanel();
        JOptionPane.showOptionDialog(parentContainer,
                                     dialogPanel,
                                     category.getName(),
                                     JOptionPane.DEFAULT_OPTION,
                                     JOptionPane.PLAIN_MESSAGE,
                                     null,
                                     null,
                                     0);
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
        dialogPanel.setLayout(new BorderLayout(0, 10));
    }

    // MODIFIES: this
    // EFFECTS: adds title to dialog panel
    private void addTitle() {
        JLabel title = new JLabel("Answered Questions");
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setFont(TITLE_FONT);
        dialogPanel.add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the CENTER of the dialog panel
    private void addCenterDialogPanel() {
        centerDialogPanel = new JPanel();
        centerDialogPanel.setLayout(new GridLayout(0, 1));
        dialogPanel.add(centerDialogPanel, BorderLayout.CENTER);

        addQuestionsPanel();
    }

    // MODIFIES: this
    // EFFECTS: add question panels to center dialog panel
    private void addQuestionsPanel() {
        for (Question q : category.getAnsweredQuestions()) {
            addQuestionPanel(q);
        }
    }

    // MODIFIES: this
    // EFFECTS: add question panel to center dialog panel
    private void addQuestionPanel(Question q) {
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
        centerDialogPanel.add(questionPanel);

        addQuestion(questionPanel, q);
    }

    // MODIFIES: this, questionPanel
    // EFFECTS: adds info for answered question to questionPanel
    private void addQuestion(JPanel questionPanel, Question q) {
        JTextArea questionInfo = new JTextArea("Question: " + q.getQuestion()
                                                + "\nAnswer: " + q.getAnswer()
                                                + "\nReward: " + q.getReward());
        questionInfo.setEditable(false);
        questionInfo.setBackground(dialogPanel.getBackground());
        questionPanel.add(questionInfo);
    }
}
