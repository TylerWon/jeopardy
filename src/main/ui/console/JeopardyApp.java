package ui.console;

import model.Category;
import model.Contestant;
import model.Game;
import model.Question;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.QuestionsReader;
import persistence.Reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

// Console-based Jeopardy application
public class JeopardyApp {

    private static final String DATA_STORE = "./data/savedGame.json";
    private static final String QUESTIONS_STORE = "./data/questions.json";
    private final Scanner input;
    private final Random random;
    private final JsonWriter jsonWriter;
    private final Reader jsonReader;
    private final Reader questionsReader;
    private Game game;

    // EFFECTS: runs the jeopardy application
    public JeopardyApp() throws FileNotFoundException {
        game = new Game();
        input = new Scanner(System.in);
        random = new Random();
        jsonWriter = new JsonWriter(DATA_STORE);
        jsonReader = new JsonReader(DATA_STORE);
        questionsReader = new QuestionsReader(QUESTIONS_STORE);

        runJeopardy();
    }

    // EFFECTS: processes user input
    private void runJeopardy() {
        openingMenu();

        String command = input.next();
        processCommand(command);

        Contestant picker = firstPick();
        while (true) {
            System.out.println("\n" + picker.getName() + " pick a category.");
            Category chosenCategory = chooseCategory(picker);
            Question chosenQuestion = chooseQuestion(chosenCategory);
            picker = answerQuestion(chosenCategory, chosenQuestion);
            printEarningsSummary("Current");

            if (isOver() || askToQuit()) {
                break;
            }
        }
        endGame();
    }

    // EFFECTS: displays opening menu
    private void openingMenu() {
        System.out.println("Welcome to MenuFrame!");
        System.out.println("Select from:");
        System.out.println("\tn -> start a new game");
        System.out.println("\tl -> load game from file");
    }

    // EFFECTS: loads game from file or starts a new game based on command
    private void processCommand(String command) {
        if (command.equals("n")) {
            newGame();
        } else if (command.equals("l")) {
            loadGame();
        } else {
            System.out.println("Invalid input. Attempting to load game.");
            loadGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new game
    private void newGame() {
        System.out.println("\nHow many contestants (2-4) would you like in your game?");

        int num;
        while (true) {
            try {
                num = input.nextInt();
                if (num < 2 || num > 4) {
                    System.out.println("Please enter a number between 2 and 4");
                } else {
                    init(num);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: initializes contestants and categories
    private void init(int i) {
        try {
            game = questionsReader.read();
            createContestants(i);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + QUESTIONS_STORE);
            System.out.println("Program will terminate.");
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates i contestants, asks the user to set an alias for each contestant,
    //          and adds the contestant to this
    private void createContestants(int i) {
        String command;

        for (int k = 1; k <= i; k++) {
            String output = String.format("Enter alias for Contestant %d", k);

            System.out.println(output);
            command = input.next();

            Contestant c = new Contestant(command);
            game.addContestant(c);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + DATA_STORE);
            printContestants();
            printEarningsSummary("Current");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + DATA_STORE);
            System.out.println("New game will be created instead.");
            newGame();
        }
    }

    // EFFECTS: prints the earnings of each contestant
    private void printEarningsSummary(String s) {
        System.out.println(s + " earnings:");
        for (Contestant c : game.getContestants()) {
            int earnings = c.getEarnings();
            String name = c.getName();

            if (earnings < 0) {
                System.out.println(name + ": -$" + Integer.toString(earnings).replaceAll("-", ""));
            } else {
                System.out.println(name + ": $" + earnings);
            }
        }
    }

    // EFFECTS: prints the contestants in the game
    private void printContestants() {
        System.out.println("\nContestants in the game:");
        for (Contestant c : game.getContestants()) {
            System.out.println("- " + c.getName());
        }
    }

    // EFFECTS: randomly selects a contestant from the list of contestants
    private Contestant firstPick() {
        int randomIndex = random.nextInt(game.numberOfContestants());
        return game.getContestant(randomIndex);
    }

    // MODIFIES: this
    // EFFECTS: displays the game categories, returns the Category chosen by the user,
    //          and updates the categories picked by contestant
    private Category chooseCategory(Contestant contestant) {
        printCategories();

        // because \n after printCategories
        input.nextLine();
        while (true) {
            String command = input.nextLine();
            command = command.toUpperCase();

            for (Category c : game.getCategories()) {
                if (c.getName().equals(command)) {
                    contestant.updateCategoriesPicked(c.getName());
                    return c;
                }
            }
            System.out.println("Please pick a valid category.");
        }
    }

    // EFFECTS: prints the categories the user can choose from
    private void printCategories() {
        String output = "Categories: | ";

        for (Category c : game.getCategories()) {
            output += c.getName() + " | ";
        }

        System.out.println(output);
    }

    // EFFECTS: displays the unanswered questions from category and returns
    //          the Question chosen by the user
    private Question chooseQuestion(Category category) {
        printQuestions(category);
        int command;

        List<Question> questions = category.getUnansweredQuestions();
        while (true) {
            command = input.nextInt();

            for (Question q : questions) {
                if (q.getReward() == command) {
                    return q;
                }
            }
            System.out.println("Please pick a valid question.");
        }
    }

    // EFFECTS: prints the questions the user can choose from
    private void printQuestions(Category category) {
        List<Question> questions = category.getUnansweredQuestions();
        String output = "Questions: | ";

        for (Question q : questions) {
            output += q.getReward() + " | ";
        }

        System.out.println(output);
    }

    // MODIFIES: this, category, question
    // EFFECTS: displays question and allows first contestant to "buzz in" to answer
    //
    //          if the contestant is correct: add earnings to the contestant, update
    //          contestant statistics, move question from unanswered questions to answered
    //          questions in category, and check if the category has anymore unanswered questions
    //
    //          otherwise: deduct earnings from the contestant, update contestant statistics,
    //          and allow other contestants to answer
    private Contestant answerQuestion(Category category, Question question) {
        String contestantAnswer;

        if (question.isDailyDouble()) {
            System.out.println("\nD A I L Y  D O U B L E");
            question.doubleReward();
        }

        System.out.println("\nQuestion: " + question.getQuestion());
        contestantAnswerNumbers();

        while (true) {
            Contestant answerer = buzzIn();
            System.out.println("\n" + answerer.getName() + " type in your answer:");

            input.nextLine();
            contestantAnswer = input.nextLine();

            if (isCorrect(question, contestantAnswer)) {
                correctAnswer(category, question, answerer);
                System.out.println("\nCorrect!");
                return answerer;
            } else {
                wrongAnswer(question, answerer);
                System.out.println("\nIncorrect. Other contestants enter your number to answer.");
            }
        }
    }

    // EFFECTS: prints which number the contestants have to input to answer the question
    //          1 = contestant 1, 2 = contestant 2, 3 = contestant 3, 4 = contestant 4
    private void contestantAnswerNumbers() {
        String output = "";

        for (int i = 0; i < game.numberOfContestants(); i++) {
            String name = game.getContestant(i).getName();
            output += name + " type " + (i + 1) + " to answer.\n";
        }
        System.out.print(output);
    }

    // EFFECTS: determines which contestant gets to answer the question
    //          1 = contestant 1, 2 = contestant, 3 = contestant 3, 4 = contestant 4
    private Contestant buzzIn() {
        int command;

        while (true) {
            try {
                command = input.nextInt();

                if (command == 1) {
                    return game.getContestant(0);
                } else if (command == 2) {
                    return game.getContestant(1);
                } else if (command == 3) {
                    return game.getContestant(2);
                } else if (command == 4) {
                    return game.getContestant(3);
                } else {
                    System.out.println("Try again. There are no contestants who can use that number to answer.");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Try again. There are no contestants who can use that number to answer.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: formats contestantAnswer so it is lower case then
    //          returns true if the answer is correct, false otherwise
    private boolean isCorrect(Question question, String contestantAnswer) {
        String questionAnswer = question.getAnswer();
        contestantAnswer = contestantAnswer.toLowerCase();

        return questionAnswer.equals(contestantAnswer);
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

        checkCategoryComplete(category);
    }

    // MODIFIES: this
    // EFFECTS: if category has no more unanswered questions, remove it from game categories,
    //          otherwise do nothing
    private void checkCategoryComplete(Category category) {
        if (category.getUnansweredQuestions().size() == 0) {
            game.removeCategory(category);
        }
    }

    // MODIFIES: contestant
    // EFFECTS: deducts earnings from the contestant, update contestant statistics
    private void wrongAnswer(Question question, Contestant contestant) {
        int reward = question.getReward();

        contestant.deductEarnings(reward);

        contestant.updateAnswerCounter(false);
    }

    // EFFECTS: returns true if the user wants to end the game, false otherwise
    private boolean askToQuit() {
        String command;

        System.out.println("If you would like to end the game here, type 'quit'. Otherwise type 'no'.");
        command = input.next();

        if (command.equals("quit")) {
            askToSave();
            return true;
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: saves game to file if user wants to save
    private void askToSave() {
        String command;

        System.out.println("If you would like to save your game, type 'save'. Otherwise, type 'no'.");
        command = input.next();

        if (command.equals("save")) {
            try {
                jsonWriter.open();
                jsonWriter.write(game);
                jsonWriter.close();
                System.out.println("\nSaved game to " + DATA_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("\nUnable to write to file: " + DATA_STORE);
                System.out.println("The game will not be saved.");
            }
        }
    }

    // EFFECTS: checks gameCategories is empty
    private boolean isOver() {
        return game.numberOfCategories() == 0;
    }

    // EFFECTS: prints out final standings, contestant statistics, and announces game is over
    private void endGame() {
        printEarningsSummary("\nFinal");
        printContestantStatistics();

        System.out.println("\nGame over. Thanks for playing!");
    }

    // source: https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
    // EFFECTS: for each contestant, prints percentage of questions answered correctly
    //          and their favorite category
    private void printContestantStatistics() {
        printPercentageOfQuestionsAnsweredCorrectly();
        printFavoriteCategory();
    }

    // EFFECTS: prints each contestants' percentage of questions answered correctly
    private void printPercentageOfQuestionsAnsweredCorrectly() {
        System.out.println("Questions answered correctly:");
        for (Contestant c : game.getContestants()) {
            double percentageCorrect = c.calculateCorrectness();
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            System.out.println(c.getName() + " : %" + numberFormat.format(percentageCorrect));
        }
    }

    // EFFECTS: prints each contestants' favorite category
    private void printFavoriteCategory() {
        System.out.println("Favorite category:");
        for (Contestant c : game.getContestants()) {
            Map<String, Integer> favoriteCategory = c.favoriteCategory();
            for (String key : favoriteCategory.keySet()) {
                int numTimesPicked = favoriteCategory.get(key);
                if (key == "None") {
                    System.out.println(c.getName() + ": did not pick any categories");
                } else {
                    System.out.println(c.getName() + ": picked " + key + " " + numTimesPicked + " time(s)!");
                }
            }
        }
    }
}
