package persistence;

import model.Category;
import model.Contestant;
import model.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo - based on JsonReader class
// Represents a reader that reads a Game from saved JSON data stored in savedData.json
public class JsonReader extends Reader {

    // EFFECTS: creates a reader to read from source file
    public JsonReader(String source) {
        super(source);
    }

    @Override
    // EFFECTS: parses Game from JSON object and returns it
    protected Game parseGame(JSONObject jsonObject) {
        Game game = new Game();
        JSONArray contestants = jsonObject.getJSONArray("Contestants");
        JSONObject categories = jsonObject.getJSONObject("Categories");
        addContestants(game, contestants);
        addCategories(game, categories);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses contestants from JSON array and adds them to game
    private void addContestants(Game game, JSONArray contestants) {
        for (Object json : contestants) {
            JSONObject contestant = (JSONObject) json;
            addContestant(game, contestant);
        }
    }

    // MODIFIES: game
    // EFFECTS: parses Contestant from JSON object and adds it to game
    private void addContestant(Game game, JSONObject contestant) {
        String name = contestant.getString("Name");
        int earnings = contestant.getInt("Earnings");
        double totalQuestionAnswered = contestant.getDouble("Total questions answered");
        double questionsAnsweredCorrectly = contestant.getDouble("Questions answered correctly");
        JSONObject categoriesPicked = contestant.getJSONObject("Categories picked");
        Map<String, Integer> categoriesPickedMap = getCategoriesPicked(categoriesPicked);

        Contestant contestantToAdd = new Contestant(name, earnings, totalQuestionAnswered,
                questionsAnsweredCorrectly, categoriesPickedMap);

        game.addContestant(contestantToAdd);
    }

    // EFFECTS: gets key, value pairs of categoriesPicked and puts into a Map
    private Map<String, Integer> getCategoriesPicked(JSONObject categoriesPicked) {
        Map<String, Integer> categoriesPickedMap = new HashMap<>();
        for (String key : categoriesPicked.keySet()) {
            int value = categoriesPicked.getInt(key);
            categoriesPickedMap.put(key, value);
        }
        return categoriesPickedMap;
    }

    // MODIFIES: game
    // EFFECTS: parses categories from JSON object and adds them to game
    protected void addCategories(Game game, JSONObject categories) {
        for (String key : categories.keySet()) {
            Category categoryToAdd = new Category(key);
            JSONArray questions = categories.getJSONArray(key);
            addQuestions(categoryToAdd, questions);

            game.addCategory(categoryToAdd);
        }
    }
}
