package model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Represents a contestant having a name, tallies of what categories they picked/questions
// they answered, and earnings
public class Contestant {

    private String name;
    private int earnings;
    private double totalQuestionsAnswered;
    private double questionsAnsweredCorrectly;
    private Map<String, Integer> categoriesPicked;

    // EFFECTS: creates a new contestant for a new game
    public Contestant(String name) {
        this.name = name;
        earnings = 0;
        totalQuestionsAnswered = 0;
        questionsAnsweredCorrectly = 0;
        categoriesPicked = new HashMap<>();
    }

    // EFFECTS: creates a new contestant for a saved game
    public Contestant(String name, int earnings, double totalQuestionsAnswered,
                      double questionsAnsweredCorrectly, Map<String, Integer> categoriesPicked) {
        this.name = name;
        this.earnings = earnings;
        this.totalQuestionsAnswered = totalQuestionsAnswered;
        this.questionsAnsweredCorrectly = questionsAnsweredCorrectly;
        this.categoriesPicked = categoriesPicked;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds amount to the total earnings of contestant
    public void addEarnings(int amount) {
        earnings += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: deducts amount from the total earnings of contestant
    public void deductEarnings(int amount) {
        earnings -= amount;
    }

    // MODIFIES: this
    // EFFECTS: if b is true, add one to the total questions the contestant answered
    //          and the questions the contestant answered correctly.
    //          otherwise, only add one to the total questions the contestant answered
    public void updateAnswerCounter(boolean b) {
        if (b) {
            questionsAnsweredCorrectly++;
        }
        totalQuestionsAnswered++;
    }

    // EFFECTS: returns 0 if the contestant has not answered any questions,
    //          otherwise returns the percentage of questions answered correctly
    public double calculateCorrectness() {
        if (totalQuestionsAnswered == 0) {
            return 0;
        }
        return (questionsAnsweredCorrectly / totalQuestionsAnswered) * 100;
    }

    // source: https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
    // MODIFIES: this
    // EFFECTS: adds one to the number of times the contestant picked a question
    //          from the category named categoryName
    public void updateCategoriesPicked(String categoryName) {
        if (categoriesPicked.containsKey(categoryName)) {
            int value = categoriesPicked.get(categoryName);
            value++;
            categoriesPicked.replace(categoryName, value);
        } else {
            categoriesPicked.put(categoryName, 1);
        }
    }

    // sources: https://www.geeksforgeeks.org/iterate-map-java/
    //          https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
    // EFFECTS: returns "none" if the contestant has not picked any categories,
    //          otherwise returns the category the contestant picked the most and the
    //          number of times picked.
    //          if the most number of times picked is the same for multiple categories,
    //          returns any of those categories and number of times picked
    public Map<String, Integer> favoriteCategory() {
        Map<String, Integer> favoriteCategory = new HashMap<>();
        String favoriteCategoryKey = null;
        int counter = 0;

        if (categoriesPicked.size() == 0) {
            favoriteCategory.put("None", 0);
        } else {
            for (String key : categoriesPicked.keySet()) {
                if (counter == 0) {
                    favoriteCategory.put(key, categoriesPicked.get(key));
                    favoriteCategoryKey = key;
                } else if (favoriteCategory.get(favoriteCategoryKey) < categoriesPicked.get(key)) {
                    favoriteCategory.put(key, categoriesPicked.get(key));
                }
                counter++;
            }
        }

        return favoriteCategory;
    }

    // EFFECTS: returns this as a JSON object
    public JSONObject toJson() {
        JSONObject jsonObjectContestant = new JSONObject();
        jsonObjectContestant.put("Name", name);
        jsonObjectContestant.put("Earnings", earnings);
        jsonObjectContestant.put("Total questions answered", totalQuestionsAnswered);
        jsonObjectContestant.put("Questions answered correctly", questionsAnsweredCorrectly);
        jsonObjectContestant.put("Categories picked", categoriesPicked);

        return jsonObjectContestant;
    }

    // getters
    public int getEarnings() {
        return earnings;
    }

    public String getName() {
        return name;
    }

    public double getTotalQuestionsAnswered() {
        return totalQuestionsAnswered;
    }

    public double getQuestionsAnsweredCorrectly() {
        return questionsAnsweredCorrectly;
    }

    public Map<String, Integer> getCategoriesPicked() {
        return categoriesPicked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contestant that = (Contestant) o;
        return earnings == that.earnings
                && Double.compare(that.questionsAnsweredCorrectly, questionsAnsweredCorrectly) == 0
                && Double.compare(that.totalQuestionsAnswered, totalQuestionsAnswered) == 0
                && Objects.equals(name, that.name)
                && Objects.equals(categoriesPicked, that.categoriesPicked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, earnings, totalQuestionsAnswered, questionsAnsweredCorrectly, categoriesPicked);
    }
}
