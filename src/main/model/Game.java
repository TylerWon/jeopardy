package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a game with categories and contestants
// INVARIANT: a game has 5 categories, and 2-4 contestants
public class Game {

    private List<Category> categories;
    private List<Contestant> contestants;

    // EFFECTS: create a new game
    public Game() {
        categories = new ArrayList<>();
        contestants = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a Category to the list of categories
    public void addCategory(Category category) {
        categories.add(category);
    }

    // REQUIRES: category is in list of categories
    // MODIFIES: this
    // EFFECTS: removes a Category from the list of categories
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    // REQUIRES: there is a Category at index in the list of categories
    // EFFECTS: returns the Category at index in the list of categories
    public Category getCategory(int index) {
        return categories.get(index);
    }

    // EFFECTS: returns the number of categories in the list of categories
    public int numberOfCategories() {
        return categories.size();
    }

    // MODIFIES: this
    // EFFECTS: adds a Contestant to the list of contestants
    public void addContestant(Contestant contestant) {
        contestants.add(contestant);
    }

    // EFFECTS: returns the number of contestants in the list of contestants
    public int numberOfContestants() {
        return contestants.size();
    }

    // REQUIRES: there is a Contestant at index in the list of contestants
    // EFFECTS: returns the Contestant at index in the list of contestants
    public Contestant getContestant(int index) {
        return contestants.get(index);
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Contestants", contestantsToJson());
        jsonObject.put("Categories", categoriesToJson());

        return jsonObject;
    }

    // EFFECTS: returns contestants in game as a JSON array
    private JSONArray contestantsToJson() {
        JSONArray jsonArrayContestants = new JSONArray();
        for (Contestant c : contestants) {
            jsonArrayContestants.put(c.toJson());
        }

        return jsonArrayContestants;
    }

    // EFFECTS: returns categories in game as a JSON object
    private JSONObject categoriesToJson() {
        JSONObject jsonObjectCategories = new JSONObject();
        for (Category c : categories) {
            jsonObjectCategories.put(c.getName(), c.toJson());
        }

        return jsonObjectCategories;
    }

    // getters
    public List<Category> getCategories() {
        return categories;
    }

    public List<Contestant> getContestants() {
        return contestants;
    }
}
