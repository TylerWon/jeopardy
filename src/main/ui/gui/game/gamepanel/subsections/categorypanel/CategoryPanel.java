package ui.gui.game.gamepanel.subsections.categorypanel;

import model.Category;
import model.Contestant;
import model.Question;
import ui.gui.game.gamepanel.GamePanel;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

// Represents a panel which displays the categories
public class CategoryPanel extends JPanel {

    private static final Font CATEGORY_FONT = new Font("", Font.BOLD, 14);
    private static final Font QUESTION_FONT = new Font("", Font.BOLD, 32);
    private static final File DAILY_DOUBLE_SOUND = new File("./data/sounds/dailyDouble.wav");

    private JButton categoryButton;
    private JButton twoHundredButton;
    private JButton fourHundredButton;
    private JButton sixHundredButton;
    private JButton eightHundredButton;
    private JButton oneThousandButton;
    private GamePanel container;

    private Category category;

    // EFFECTS: constructs a panel which displays the categories
    public CategoryPanel(GamePanel container, Category category) {
        initializeFields(container, category);
        initializeGraphics();
        addCategory();
        addQuestions();
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields for this
    private void initializeFields(GamePanel container, Category category) {
        this.container = container;
        this.category = category;
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics for this
    private void initializeGraphics() {
        setLayout(new GridLayout(6, 1));
    }

    // MODIFIES: this
    // EFFECTS: adds category button to this
    private void addCategory() {
        categoryButton = new JButton(category.getName());
        categoryButton.setFocusable(false);
        categoryButton.setFont(CATEGORY_FONT);
        categoryButton.addActionListener(new CategoryButtonListener());

        add(categoryButton);
    }

    // MODIFIES: this
    // EFFECTS: adds question buttons to this
    private void addQuestions() {
        setupButtons();
        List<Integer> unansweredQuestions = getUnansweredQuestions();

        disableButtons(unansweredQuestions);
    }

    // MODIFIES: this
    // EFFECTS: initializes question buttons
    private void setupButtons() {
        setupTwoHundredButton();
        setupFourHundredButton();
        setupSixHundredButton();
        setupEightHundredButton();
        setupOneThousandButton();
    }

    // MODIFIES: this
    // EFFECTS: sets up the two hundred question button
    private void setupTwoHundredButton() {
        twoHundredButton = new JButton("200");
        initializeButton(twoHundredButton);
        add(twoHundredButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the four hundred question button
    private void setupFourHundredButton() {
        fourHundredButton = new JButton("400");
        initializeButton(fourHundredButton);
        add(fourHundredButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the six hundred question button
    private void setupSixHundredButton() {
        sixHundredButton = new JButton("600");
        initializeButton(sixHundredButton);
        add(sixHundredButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the two hundred question button
    private void setupEightHundredButton() {
        eightHundredButton = new JButton("800");
        initializeButton(eightHundredButton);
        add(eightHundredButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the two hundred question button
    private void setupOneThousandButton() {
        oneThousandButton = new JButton("1000");
        initializeButton(oneThousandButton);
        add(oneThousandButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes properties of button
    private void initializeButton(JButton button) {
        button.setFocusable(false);
        button.setFont(QUESTION_FONT);
        button.addActionListener(new QuestionButtonListener());
    }

    // EFFECTS: returns the questions (represented by their reward amount)
    //          that are unanswered in the category
    private List<Integer> getUnansweredQuestions() {
        List<Integer> unansweredQuestions = new ArrayList<>();
        List<Question> questions = category.getUnansweredQuestions();

        if (questions.isEmpty()) {
            return unansweredQuestions;
        }

        for (Question q : questions) {
            unansweredQuestions.add(q.getReward());
        }

        return  unansweredQuestions;
    }

    // MODIFIES: this
    // EFFECTS: disable button if there is no corresponding question in unanswered questions
    private void disableButtons(List<Integer> unansweredQuestions) {
        if (unansweredQuestions.isEmpty()) {
            twoHundredButton.setEnabled(false);
            fourHundredButton.setEnabled(false);
            sixHundredButton.setEnabled(false);
            eightHundredButton.setEnabled(false);
            oneThousandButton.setEnabled(false);
        }

        if (!unansweredQuestions.contains(200)) {
            twoHundredButton.setEnabled(false);
        }

        if (!unansweredQuestions.contains(400)) {
            fourHundredButton.setEnabled(false);
        }

        if (!unansweredQuestions.contains(600)) {
            sixHundredButton.setEnabled(false);
        }

        if (!unansweredQuestions.contains(800)) {
            eightHundredButton.setEnabled(false);
        }

        if (!unansweredQuestions.contains(1000)) {
            oneThousandButton.setEnabled(false);
        }
    }

    // Source: https://coderanch.com/t/341739/java/play-wav-file-button-pressed
    // EFFECTS: plays time's up! sound
    public void playSound(File file) {
        try {
            AudioClip sound = Applet.newAudioClip(file.toURL());
            sound.play();
        } catch (MalformedURLException exception) {
            System.out.println("Sound was not played.");
        }
    }

    // EFFECTS: returns the frame this is in
    public JFrame getFrame() {
        return container.getFrame();
    }

    // EFFECTS: returns the picker
    public Contestant getPicker() {
        return container.getPicker();
    }

    // getters
    public GamePanel getContainer() {
        return container;
    }

    public Category getCategory() {
        return category;
    }

    // Represents an ActionListener which listens for when the category button is pressed
    private class CategoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == categoryButton) {
                new CategoryInfoDialog(CategoryPanel.this, category);
            }
        }
    }

    // Represents an ActionListener which listens for when a question button is pressed
    private class QuestionButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == twoHundredButton) {
                Question question = getQuestion(200);
                checkDailyDouble(question);
                moveToQuestion(twoHundredButton, question);

            } else if (e.getSource() == fourHundredButton) {
                Question question = getQuestion(400);
                checkDailyDouble(question);
                moveToQuestion(fourHundredButton, question);

            } else if (e.getSource() == sixHundredButton) {
                Question question = getQuestion(600);
                checkDailyDouble(question);
                moveToQuestion(sixHundredButton, question);

            } else if (e.getSource() == eightHundredButton) {
                Question question = getQuestion(800);
                checkDailyDouble(question);
                moveToQuestion(eightHundredButton, question);

            } else if (e.getSource() == oneThousandButton) {
                Question question = getQuestion(1000);
                checkDailyDouble(question);
                moveToQuestion(oneThousandButton, question);
            }
        }

        // EFFECTS: returns the question from category with reward amount
        private Question getQuestion(int reward) {
            Question question = null;

            for (Question q : category.getUnansweredQuestions()) {
                if (q.getReward() == reward) {
                    question = q;
                }
            }
            return question;
        }

        // EFFECTS: checks if question is a daily double
        //          if it is, notifies the user and double the question reward amount
        private void checkDailyDouble(Question question) {
            if (question.isDailyDouble()) {
                dailyDoubleMessage();
                question.doubleReward();
            }
        }

        // EFFECTS: notifies the user that the question is a daily double
        private void dailyDoubleMessage() {
            playSound(DAILY_DOUBLE_SOUND);
            JOptionPane.showMessageDialog(container, "DAILY DOUBLE!", null, JOptionPane.PLAIN_MESSAGE);
        }

        // MODIFIES: CategoryPanel
        // EFFECTS: updates categories picked by contestant, disables button, displays question
        private void moveToQuestion(JButton button, Question question) {
            Contestant picker = container.getPicker();

            picker.updateCategoriesPicked(category.getName());
            button.setEnabled(false);
            container.setVisible(false);

            container.getFrame().add(new QuestionPanel(CategoryPanel.this, container.getGame(), question));
        }
    }
}
