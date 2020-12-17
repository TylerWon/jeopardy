package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    Question testQuestion;

    @BeforeEach
    public void setup() {
        testQuestion = new Question("What is your name?", "Tyler", 200, false);
    }

    @Test
    public void testConstructor() {
        assertEquals("What is your name?", testQuestion.getQuestion());
        assertEquals("Tyler", testQuestion.getAnswer());
        assertEquals(200, testQuestion.getReward());
        assertFalse(testQuestion.isDailyDouble());
    }

    @Test
    public void testSetDailyDoubleTrue() {
        testQuestion.setDailyDouble(false);
        assertFalse(testQuestion.isDailyDouble());
        testQuestion.setDailyDouble(true);
        assertTrue(testQuestion.isDailyDouble());
    }

    @Test
    public void testSetDailyDoubleFalse() {
        testQuestion.setDailyDouble(true);
        assertTrue(testQuestion.isDailyDouble());
        testQuestion.setDailyDouble(false);
        assertFalse(testQuestion.isDailyDouble());
    }

    @Test
    public void testSetAnsweredTrue() {
        testQuestion.setAnswered(false);
        assertFalse(testQuestion.isAnswered());
        testQuestion.setAnswered(true);
        assertTrue(testQuestion.isAnswered());
    }

    @Test
    public void testSetAnsweredFalse() {
        testQuestion.setAnswered(true);
        assertTrue(testQuestion.isAnswered());
        testQuestion.setAnswered(false);
        assertFalse(testQuestion.isAnswered());
    }

    @Test
    public void testSetReward() {
        testQuestion.doubleReward();
        assertEquals(400, testQuestion.getReward());
    }

    @Test
    public void testEqualsSameObjectReference() {
        assertTrue(testQuestion.equals(testQuestion));
    }

    @Test
    public void testEqualsSameObject() {
        Question testQuestion2 = new Question("What is your name?", "Tyler", 200, false);
        assertTrue(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsDifferentReward() {
        Question testQuestion2 = new Question("What is your name?", "Tyler", 400, false);
        assertFalse(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsDifferentDailyDouble() {
        Question testQuestion2 = new Question("What is your name?", "Tyler", 200, true);
        assertFalse(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsDifferentAnswered() {
        Question testQuestion2 = new Question("What is your name?", "Tyler", 200, false);
        testQuestion2.setAnswered(true);
        assertFalse(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsDifferentQuestion() {
        Question testQuestion2 = new Question("What is your favorite toy?", "Tyler", 200, false);
        assertFalse(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsDifferentAnswer() {
        Question testQuestion2 = new Question("What is your name?", "Connor", 200, false);
        assertFalse(testQuestion.equals(testQuestion2));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(testQuestion.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        Category testCategory = new Category("Cars");
        assertFalse(testQuestion.equals(testCategory));
    }

    @Test
    public void testHashCodeSameObject() {
        Question testQuestion2 = new Question("What is your name?", "Tyler", 200, false);
        assertEquals(testQuestion2.hashCode(), testQuestion.hashCode());
    }
}
