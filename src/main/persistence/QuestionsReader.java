package persistence;

import model.Category;
import model.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo - based on JsonReader class
// Represents a reader that reads a Game from JSON data stored in questions.json
public class QuestionsReader extends Reader {

    private Random random;

    // EFFECTS: creates a reader to read from source file
    public QuestionsReader(String source) {
        super(source);
        random = new Random();
    }

    @Override
    // EFFECTS: parses Game from JSON object and returns it
    protected Game parseGame(JSONObject categories) {
        Game game = new Game();
        addCategories(game, categories);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses categories from JSON Object and adds them to game
    protected void addCategories(Game game, JSONObject categories) {
        List<String> selectedCategories = selectCategories(categories);

        for (String categoryName : selectedCategories) {
            Category category = new Category(categoryName);
            JSONArray questions = categories.getJSONArray(categoryName);
            addQuestions(category, questions);

            game.addCategory(category);
        }
    }

    // EFFECTS: returns the names of 5 randomly selected categories
    private List<String> selectCategories(JSONObject categories) {
        List<String> allCategories = new ArrayList<>();
        List<String> selectedCategories = new ArrayList<>();

        for (String category : categories.keySet()) {
            allCategories.add(category);
        }

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(allCategories.size());
            String categoryName = allCategories.get(randomIndex);
            selectedCategories.add(categoryName);
            allCategories.remove(randomIndex);
        }

        return selectedCategories;
    }
}
