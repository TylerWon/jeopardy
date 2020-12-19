package ui.gui.game.gamepanel.subsections.categorypanel;

import model.Category;
import model.Contestant;
import model.Game;
import model.Question;
import ui.gui.game.gamepanel.GamePanel;
import ui.gui.gameover.GameOverFrame;

import javax.swing.*;
import javax.swing.text.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;

// Represents a panel which displays the question chosen by the user
public class QuestionPanel extends JPanel {

    private static final Font QUESTION_FONT = new Font("", Font.BOLD, 50);
    private static final Font BUTTON_FONT = new Font("", Font.BOLD, 12);
    private static final File TIMES_UP_SOUND = new File("./data/sounds/timesUp.wav");
    private static final File CORRECT_SOUND = new File("./data/sounds/correct.wav");
    private static final File INCORRECT_SOUND = new File("./data/sounds/incorrect.wav");

    private JButton skip;
    private JPanel centerPanel;
    private JPanel southPanel;
    private KeyListener keyListener;
    private JPanel container;
    private GamePanel parentContainer;
    private JFrame frame;

    private Game game;
    private Category category;
    private Question question;
    private Contestant picker;

    // EFFECTS: constructs a new question panel which displays the question chosen by the user
    public QuestionPanel(JFrame frame, JPanel container, GamePanel parentContainer, Game game,
                         Category category, Question question, Contestant picker) {
        initializeFields(frame, container, parentContainer, game, category, question, picker);
        initializeGraphics();
        addCenterPanel();
        addSouthPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields for this
    private void initializeFields(JFrame frame, JPanel container, GamePanel parentContainer, Game game,
                                  Category category, Question question, Contestant picker) {

        this.frame = frame;
        this.container = container;
        this.parentContainer = parentContainer;
        this.game = game;
        this.category = category;
        this.question = question;
        this.picker = picker;
        keyListener = new BuzzInListener();
    }

    // MODIFIES: this
    // EFFECTS: initializes graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout(20, 20));
        frame.addKeyListener(keyListener);
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the CENTER of this
    private void addCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        add(centerPanel, BorderLayout.CENTER);

        addQuestion();
    }

    // MODIFIES: this
    // EFFECTS: adds question to this
    private void addQuestion() {
        StyledDocument document = styleDocument();
        JTextPane questionPrompt = new JTextPane(document);

        questionPrompt.setFont(QUESTION_FONT);
        questionPrompt.setBackground(this.getBackground());
        questionPrompt.setEditable(false);
        centerPanel.add(questionPrompt);
    }

    // EFFECTS: creates a styled document which is center-aligned
    private StyledDocument styleDocument() {
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        try {
            document.insertString(document.getLength(), question.getQuestion().toUpperCase(), style);
        } catch (BadLocationException e) {
            String message = "A problem was encountered when trying to load the question."
                    + "\nThe program will terminate.";
            JOptionPane.showMessageDialog(QuestionPanel.this, message,
                    "Error: Unable to Load Question", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        return document;
    }

    // MODIFIES: this
    // EFFECTS: adds a new panel to the SOUTH of this
    private void addSouthPanel() {
        southPanel = new JPanel();
        add(southPanel, BorderLayout.SOUTH);

        addSkipButton();
    }

    // MODIFIES: this
    // EFFECTS: adds skip button to this
    private void addSkipButton() {
        skip = new JButton("Skip");
        skip.setFocusable(false);
        skip.addActionListener(new SkipButtonListener());
        skip.setFont(BUTTON_FONT);
        southPanel.add(skip);
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

    // MODIFIES: this
    // EFFECTS: remove KeyListener, update game panel graphics, set game panel to visible
    public void moveToGamePanel(Contestant contestant) {
        frame.removeKeyListener(keyListener);
        parentContainer.update(contestant);
        setVisible(false);
        parentContainer.setVisible(true);
    }

    // EFFECTS: if the number of categories completed == 5, end the game,
    //          otherwise, do nothing
    public Boolean isGameOver() {
        int counter = 0;

        for (Category c : game.getCategories()) {
            if (c.numberOfUnansweredQuestions() == 0) {
                counter++;
            }
        }

        if (counter == 5) {
            return true;
        }
        return false;
    }

    // Represents a KeyListener which listens for "buzz-in" from different contestants
    private class BuzzInListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'a') {
                Contestant contestant = game.getContestant(0);
                evaluateAnswer(contestant);

            } else if (e.getKeyChar() == 'c') {
                Contestant contestant = game.getContestant(1);
                evaluateAnswer(contestant);

            } else if (e.getKeyChar() == 'n') {
                try {
                    Contestant contestant = game.getContestant(2);
                    evaluateAnswer(contestant);
                } catch (IndexOutOfBoundsException exp) {
                    displayContestantNotInGameDialog(game.numberOfContestants());
                }

            } else if (e.getKeyChar() == 'l') {
                try {
                    Contestant contestant = game.getContestant(3);
                    evaluateAnswer(contestant);
                } catch (IndexOutOfBoundsException exp) {
                    displayContestantNotInGameDialog(game.numberOfContestants());
                }
            }
        }

        // EFFECTS: notifies the user that contestant is not in game
        private void displayContestantNotInGameDialog(int numOfPlayers) {
            if (numOfPlayers == 2) {
                JOptionPane.showMessageDialog(QuestionPanel.this,
                        "Contestant 1 press 'a' to answer."
                                + "\nContestant 2 press 'c' to answer.",
                        "Error!",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(QuestionPanel.this,
                        "Contestant 1 press 'a' to answer."
                                + "\nContestant 2 press 'c' to answer."
                                + "\nContestant 3 press 'n' to answer.",
                        "Error!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        // EFFECTS: evaluates answer from user
        //          if the contestant is correct: add earnings to the contestant, update
        //          contestant statistics, move question from unanswered questions to answered
        //          questions in category
        //
        //          otherwise: deduct earnings from the contestant, update contestant statistics,
        //          and allow other contestants to answer
        private void evaluateAnswer(Contestant contestant) {
            String answer = answer(contestant);

            if (isCorrect(question, answer)) {
                playSound(CORRECT_SOUND);
                correctAnswer(category, question, contestant);

                if (isGameOver()) {
                    frame.dispose();
                    new GameOverFrame(game);
                } else {
                    correctNotification(contestant);
                    moveToGamePanel(contestant);
                }

            } else {
                playSound(INCORRECT_SOUND);
                wrongAnswer(question, contestant);
                incorrectNotification();
            }
        }

        // EFFECTS: removes KeyListener from frame (so user input does not activate key listener)
        //          and returns the answer from the user
        private String answer(Contestant contestant) {
            String answer;

            while (true) {
                answer = JOptionPane.showInputDialog(QuestionPanel.this,
                                                    contestant.getName() + ", enter your answer",
                                                    null,
                                                    JOptionPane.PLAIN_MESSAGE);
                if (answer != null) {
                    break;
                }
            }

            return answer;
        }

        // EFFECTS: formats contestantAnswer so it is lower case then
        //          returns true if the answer is correct, false otherwise
        private boolean isCorrect(Question question, String contestantAnswer) {
            String questionAnswer = question.getAnswer().toLowerCase();
            contestantAnswer = contestantAnswer.toLowerCase();

            return questionAnswer.equals(contestantAnswer);
        }

        // EFFECTS: notifies the user that their answer was correct
        private void correctNotification(Contestant contestant) {
            JOptionPane.showMessageDialog(QuestionPanel.this,
                    "Correct! " + contestant.getName() + ", pick a category.",
                    null,
                    JOptionPane.PLAIN_MESSAGE);
        }

        // MODIFIES: category, question, contestant
        // EFFECTS: adds earnings to the contestant, update contestant statistics,
        //          move question from unanswered questions to answered questions in category,
        //          and check if category has anymore questions
        private void correctAnswer(Category category, Question question, Contestant contestant) {
            int reward = question.getReward();

            contestant.addEarnings(reward);

            contestant.updateAnswerCounter(true);

            question.setAnswered(true);
            category.moveToAnsweredQuestions(question);
        }

        // MODIFIES: contestant
        // EFFECTS: deducts earnings from the contestant, update contestant statistics
        private void wrongAnswer(Question question, Contestant contestant) {
            int reward = question.getReward();

            contestant.deductEarnings(reward);

            contestant.updateAnswerCounter(false);
        }

        // EFFECTS: notifies the user that their answer is incorrect
        private void incorrectNotification() {
            JOptionPane.showMessageDialog(QuestionPanel.this,
                    "Incorrect. Other contestants buzz in to answer.",
                    null,
                    JOptionPane.PLAIN_MESSAGE);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // do nothing
        }
    }

    // Represents an ActionListener which listens for when the skip button is pressed
    private class SkipButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == skip) {
                playSound(TIMES_UP_SOUND);
                updateCategory();
                displayAnswer();

                if (isGameOver()) {
                    frame.dispose();
                    new GameOverFrame(game);
                } else {
                    pickNotification();
                    moveToGamePanel(picker);
                }
            }
        }

        // MODIFIES: this
        // EFFECTS: sets question to answered and moves it to the list of
        //          answered questions in category
        private void updateCategory() {
            question.setAnswered(true);
            category.moveToAnsweredQuestions(question);
        }

        // EFFECTS: displays the correct answer as a dialog
        private void displayAnswer() {
            JOptionPane.showMessageDialog(QuestionPanel.this,
                    "The answer is: " + question.getAnswer(),
                    null, JOptionPane.PLAIN_MESSAGE);
        }

        // EFFECTS: notifies the user of which contestant is picking
        private void pickNotification() {
            JOptionPane.showMessageDialog(QuestionPanel.this,
                    picker.getName() + ", it is your pick!",
                    null, JOptionPane.PLAIN_MESSAGE);
        }
    }
}
