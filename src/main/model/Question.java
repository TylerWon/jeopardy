package model;

import org.json.JSONObject;

import java.util.Objects;

// Represents a question having a question, answer, reward, whether it is a daily double,
// and whether it has been answered
public class Question {

    private String question;
    private String answer;
    private int reward;
    private boolean dailyDouble;
    private boolean answered;

    // REQUIRES: reward > 0
    // EFFECTS: creates a new question with an answer, reward, and whether it is a daily double
    public Question(String question, String answer, int reward, boolean dailyDouble) {
        this.question = question;
        this.answer = answer;
        this.reward = reward;
        this.dailyDouble = dailyDouble;
        answered = false;
    }

    // MODIFIES: this
    // EFFECTS: doubles the reward
    public void doubleReward() {
        reward = reward * 2;
    }

    // EFFECTS: returns this as a JSON object
    public JSONObject toJson() {
        JSONObject jsonObjectQuestion = new JSONObject();
        jsonObjectQuestion.put("Question", question);
        jsonObjectQuestion.put("Answer", answer);
        jsonObjectQuestion.put("Reward", reward);
        jsonObjectQuestion.put("DailyDouble", dailyDouble);

        return jsonObjectQuestion;
    }

    // getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getReward() {
        return reward;
    }

    public boolean isDailyDouble() {
        return dailyDouble;
    }

    // setters
    public void setDailyDouble(boolean b) {
        dailyDouble = b;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean b) {
        answered = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question1 = (Question) o;
        return reward == question1.reward
                && dailyDouble == question1.dailyDouble
                && answered == question1.answered
                && Objects.equals(question, question1.question)
                && Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer, reward, dailyDouble, answered);
    }
}
