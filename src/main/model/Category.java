package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a category which has answered and unanswered questions
// INVARIANT: a category has 5 questions
public class Category {

    private final String name;
    private final List<Question> unansweredQuestions;
    private final List<Question> answeredQuestions;

    // EFFECTS: creates a new category with  a name
    public Category(String name) {
        this.name = name;
        unansweredQuestions = new ArrayList<>();
        answeredQuestions = new ArrayList<>();
    }

    // REQUIRES: q is answered
    // MODIFIES: this
    // EFFECTS: removes q from the list of unanswered questions and adds it to the
    //          list of answered questions
    public void moveToAnsweredQuestions(Question q) {
        unansweredQuestions.remove(q);
        answeredQuestions.add(q);
    }

    // REQUIRES: q is unanswered
    // MODIFIES: this
    // EFFECTS: adds q to the list of unanswered questions
    public void addToUnansweredQuestions(Question q) {
        unansweredQuestions.add(q);
    }

    // EFFECTS: returns the number of unanswered questions
    public int numberOfUnansweredQuestions() {
        return unansweredQuestions.size();
    }

    // EFFECTS: returns the number of answered questions
    public int numberOfAnsweredQuestions() {
        return answeredQuestions.size();
    }

    // EFFECTS: returns this as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArrayQuestions = new JSONArray();
        for (Question q : unansweredQuestions) {
            jsonArrayQuestions.put(q.toJson());
        }
        return jsonArrayQuestions;
    }

    // getters
    public String getName() {
        return name;
    }

    public List<Question> getUnansweredQuestions() {
        return unansweredQuestions;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
