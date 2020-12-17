package persistence;

import model.Category;
import model.Game;
import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a general reader
public abstract class Reader {

    private String source;

    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Game from file and returns it
    //          throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    protected String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Game from JSON object and returns it
    protected abstract Game parseGame(JSONObject jsonObject);

    // MODIFIES: category
    // EFFECTS: parses questions from JSON array and adds them to category
    protected void addQuestions(Category category, JSONArray questions) {
        for (Object json : questions) {
            JSONObject question = (JSONObject) json;
            addQuestion(category, question);
        }
    }

    // MODIFIES: category
    // EFFECTS: parses question from JSON object and adds it to category
    protected void addQuestion(Category category, JSONObject question) {
        String questionPrompt = question.getString("Question");
        String answer = question.getString("Answer");
        int reward = question.getInt("Reward");
        boolean dailyDouble = question.getBoolean("DailyDouble");

        Question questionToAdd = new Question(questionPrompt, answer, reward, dailyDouble);

        category.addToUnansweredQuestions(questionToAdd);
    }
}
