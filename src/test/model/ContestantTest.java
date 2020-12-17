package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ContestantTest {

    Contestant testContestant;

    @BeforeEach
    public void setup() {
        testContestant = new Contestant("Tyler");
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals("Tyler", testContestant.getName());
    }

    @Test
    public void testConstructor() {
        Contestant testContestant2 = new Contestant("Tyler", 0, 0, 0, new HashMap<>());
        assertEquals("Tyler", testContestant2.getName());
        assertEquals(0, testContestant2.getEarnings());
        assertEquals(0, testContestant2.getTotalQuestionsAnswered());
        assertEquals(0, testContestant2.getQuestionsAnsweredCorrectly());
        assertTrue(testContestant2.getCategoriesPicked().isEmpty());
    }

    @Test
    public void testAddEarningsLowerBoundary() {
        testContestant.addEarnings(1);
        assertEquals(1, testContestant.getEarnings());
    }

    @Test
    public void testAddEarningsAnyAmount() {
        testContestant.addEarnings(400);
        assertEquals(400, testContestant.getEarnings());
    }

    @Test
    public void testDeductEarningsLowerBoundary() {
        testContestant.deductEarnings(1);
        assertEquals(-1, testContestant.getEarnings());
    }

    @Test
    public void testDeductEarningsAnyAmount() {
        testContestant.deductEarnings(400);
        assertEquals(-400, testContestant.getEarnings());
    }

    @Test
    public void testUpdateAnswerCounterFalse() {
        assertEquals(0, testContestant.getTotalQuestionsAnswered());
        assertEquals(0, testContestant.getQuestionsAnsweredCorrectly());
        testContestant.updateAnswerCounter(false);
        assertEquals(1, testContestant.getTotalQuestionsAnswered());
        assertEquals(0, testContestant.getQuestionsAnsweredCorrectly());
    }

    @Test
    public void testUpdateAnswerCounterTrue() {
        assertEquals(0, testContestant.getTotalQuestionsAnswered());
        assertEquals(0, testContestant.getQuestionsAnsweredCorrectly());
        testContestant.updateAnswerCounter(true);
        assertEquals(1, testContestant.getTotalQuestionsAnswered());
        assertEquals(1, testContestant.getQuestionsAnsweredCorrectly());
    }

    @Test
    public void testCalculateCorrectnessNoQuestionsAnswered() {
        assertEquals(0, testContestant.calculateCorrectness());
    }

    @Test
    public void testCalculateCorrectnessNoQuestionsAnsweredCorrectly() {
        testContestant.updateAnswerCounter(false);
        assertEquals(0.0, testContestant.calculateCorrectness());
    }

    @Test
    public void testCalculateCorrectnessManyQuestionsAnsweredCorrectly() {
        for (int i = 0; i < 5; i++) {
            testContestant.updateAnswerCounter(true);
        }
        assertEquals(100.0, testContestant.calculateCorrectness());
    }

    @Test
    public void testUpdateCategoriesCategoryPickedFirstTime() {
        Map<String, Integer> categoriesPicked = testContestant.getCategoriesPicked();
        assertEquals(0, categoriesPicked.size());
        testContestant.updateCategoriesPicked("Food");
        assertEquals(1, categoriesPicked.size());
        assertTrue(categoriesPicked.containsKey("Food"));
        assertTrue(categoriesPicked.containsValue(1));
    }

    @Test
    public void testUpdateCategoriesCategoryPickedBefore() {
        Map<String, Integer> categoriesPicked = testContestant.getCategoriesPicked();
        testContestant.updateCategoriesPicked("Food");
        assertEquals(1, categoriesPicked.size());
        testContestant.updateCategoriesPicked("Food");
        assertEquals(1, categoriesPicked.size());
        assertTrue(categoriesPicked.containsKey("Food"));
        assertTrue(categoriesPicked.containsValue(2));
    }

    @Test
    public void testUpdateCategoriesManyCategories() {
        Map<String, Integer> categoriesPicked = testContestant.getCategoriesPicked();
        testContestant.updateCategoriesPicked("Food");
        assertEquals(1, categoriesPicked.size());
        testContestant.updateCategoriesPicked("Cars");
        assertEquals(2, categoriesPicked.size());
        assertTrue(categoriesPicked.containsKey("Food"));
        assertTrue(categoriesPicked.containsValue(1));
        assertTrue(categoriesPicked.containsKey("Cars"));
        assertTrue(categoriesPicked.containsValue(1));
    }

    @Test
    public void testFavoriteCategoryNoCategoriesPicked() {
        Map<String, Integer> favoriteCategory = testContestant.favoriteCategory();
        assertTrue(favoriteCategory.containsKey("None"));
        assertEquals(0, favoriteCategory.get("None"));
    }

    @Test
    public void testFavoriteCategoryOneCategoryPicked() {
        testContestant.updateCategoriesPicked("Food");
        Map<String, Integer> favoriteCategory = testContestant.favoriteCategory();
        assertTrue(favoriteCategory.containsKey("Food"));
        assertEquals(1, favoriteCategory.get("Food"));
    }

    @Test
    public void testFavoriteCategoryManyCategoriesPicked() {
        testContestant.updateCategoriesPicked("Food");
        testContestant.updateCategoriesPicked("Food");
        testContestant.updateCategoriesPicked("Cars");
        Map<String, Integer> favoriteCategory = testContestant.favoriteCategory();
        assertTrue(favoriteCategory.containsKey("Food"));
        assertEquals(2, favoriteCategory.get("Food"));
    }

    @Test
    public void testFavoriteCategoryPickedCategoriesEqualNumberOfTimes() {
        testContestant.updateCategoriesPicked("Food");
        testContestant.updateCategoriesPicked("Cars");
        Map<String, Integer> favoriteCategory = testContestant.favoriteCategory();
        assertTrue(favoriteCategory.containsKey("Food") || favoriteCategory.containsKey("Cars"));
    }

    @Test
    public void testEqualsSameObjectReference() {
        assertTrue(testContestant.equals(testContestant));
    }

    @Test
    public void testEqualsSameObject() {
        Contestant testContestant2 = new Contestant("Tyler");
        assertTrue(testContestant.equals(testContestant2));
    }

    @Test
    public void testEqualsDifferentEarnings() {
        Contestant testContestant2 = new Contestant("Tyler");
        testContestant2.addEarnings(200);
        assertFalse(testContestant.equals(testContestant2));
    }

    // this might not work
    @Test
    public void testEqualsDifferentQuestionsAnsweredCorrectly() {
        Contestant testContestant2 = new Contestant("Tyler");
        testContestant2.updateAnswerCounter(true);
        assertFalse(testContestant.equals(testContestant2));
    }

    @Test
    public void testEqualsDifferentTotalQuestionsAnswered() {
        Contestant testContestant2 = new Contestant("Tyler");
        testContestant2.updateAnswerCounter(false);
        assertFalse(testContestant.equals(testContestant2));
    }

    @Test
    public void testEqualsDifferentName() {
        Contestant testContestant2 = new Contestant("Connor");
        assertFalse(testContestant.equals(testContestant2));
    }

    @Test
    public void testEqualsDifferentCategoriesPicked() {
        Contestant testContestant2 = new Contestant("Tyler");
        testContestant2.updateCategoriesPicked("IN ORDER");
        assertFalse(testContestant.equals(testContestant2));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(testContestant.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        Category testCategory = new Category("Cars");
        assertFalse(testContestant.equals(testCategory));
    }

    @Test
    public void testHashCodeSameObject() {
        Contestant testContestant2 = new Contestant("Tyler");
        assertEquals(testContestant2.hashCode(), testContestant.hashCode());
    }

}
