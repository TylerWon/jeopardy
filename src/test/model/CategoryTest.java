package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category testCategory;
    private Question testQuestion;

    @BeforeEach
    public void setup() {
        testCategory = new Category("Cars");
        testQuestion = new Question("What is your name?", "Tyler", 400, false);
    }

    @Test
    public void testConstructor() {
        assertEquals("Cars", testCategory.getName());
    }

    @Test
    public void testAddToUnansweredQuestions() {
        List<Question> unansweredQuestions = testCategory.getUnansweredQuestions();
        assertEquals(0, testCategory.numberOfUnansweredQuestions());

        testCategory.addToUnansweredQuestions(testQuestion);
        assertEquals(1, testCategory.numberOfUnansweredQuestions());

        questionChecker(unansweredQuestions.get(0), "What is your name?", "Tyler", 400, false, false);
    }

    @Test
    public void testMoveToAnsweredQuestions() {
        List<Question> unansweredQuestions = testCategory.getUnansweredQuestions();
        List<Question> answeredQuestions = testCategory.getAnsweredQuestions();
        assertEquals(0, testCategory.numberOfAnsweredQuestions());

        testCategory.addToUnansweredQuestions(testQuestion);
        testQuestion.setAnswered(true);

        testCategory.moveToAnsweredQuestions(testQuestion);
        assertEquals(1, testCategory.numberOfAnsweredQuestions());
        assertEquals(0, testCategory.numberOfUnansweredQuestions());

        questionChecker(answeredQuestions.get(0), "What is your name?", "Tyler", 400, false, true);
    }

    @Test
    public void testEqualsSameObjectReference() {
        assertTrue(testCategory.equals(testCategory));
    }

    @Test
    public void testEqualsSameObject() {
        Category testCategory2 = new Category("Cars");
        assertTrue(testCategory.equals(testCategory2));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(testCategory.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(testCategory.equals(testQuestion));
    }

    @Test
    public void testHashCodeSameObject() {
        Category testCategory2 = new Category("Cars");
        assertEquals(testCategory2.hashCode(), testCategory.hashCode());
    }

    private void questionChecker(Question q, String question, String answer,
                                 int reward, boolean dailyDouble, boolean answered) {
        assertEquals(question, q.getQuestion());
        assertEquals(answer, q.getAnswer());
        assertEquals(reward, q.getReward());
        assertEquals(dailyDouble, q.isDailyDouble());
        assertEquals(answered, q.isAnswered());
    }
}
