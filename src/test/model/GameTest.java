package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game testGame;
    private Category testCategory1;
    private Category testCategory2;
    private Contestant testContestant1;
    private Contestant testContestant2;

    @BeforeEach
    public void setup() {
        testGame = new Game();
        testCategory1 = new Category("IN ORDER");
        testCategory2 = new Category("CORPORATE LINGO");
        testContestant1 = new Contestant("Tyler");
        testContestant2 = new Contestant("Connor");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testGame.numberOfContestants());
        assertEquals(0, testGame.numberOfCategories());
    }

    @Test
    public void testAddCategoryNoCategoriesInGame() {
        testGame.addCategory(testCategory1);
        assertEquals(1, testGame.numberOfCategories());
        Category c1 = testGame.getCategory(0);
        assertEquals("IN ORDER", c1.getName());
    }

    @Test
    public void testAddCategoryManyCategoriesInGame() {
        testGame.addCategory(testCategory1);
        testGame.addCategory(testCategory2);
        assertEquals(2, testGame.numberOfCategories());
        Category c1 = testGame.getCategory(0);
        assertEquals("IN ORDER", c1.getName());
        Category c2 = testGame.getCategory(1);
        assertEquals("CORPORATE LINGO", c2.getName());
    }

    @Test
    public void removeCategoryOneCategoryInGame() {
        testGame.addCategory(testCategory1);
        testGame.removeCategory(testCategory1);
        assertEquals(0, testGame.numberOfCategories());
    }

    @Test
    public void removeCategoryManyCategoriesInGame() {
        testGame.addCategory(testCategory1);
        testGame.addCategory(testCategory2);
        testGame.removeCategory(testCategory2);
        assertEquals(1, testGame.numberOfCategories());
        Category c1 = testGame.getCategory(0);
        assertEquals("IN ORDER", c1.getName());
    }

    @Test
    public void testAddContestantNoContestantsInGame() {
        testGame.addContestant(testContestant1);
        assertEquals(1, testGame.numberOfContestants());
        Contestant c1 = testGame.getContestant(0);
        assertEquals("Tyler", c1.getName());
    }

    @Test
    public void testAddContestantManyContestantsInGame() {
        testGame.addContestant(testContestant1);
        testGame.addContestant(testContestant2);
        assertEquals(2, testGame.numberOfContestants());
        Contestant c1 = testGame.getContestant(0);
        assertEquals("Tyler", c1.getName());
        Contestant c2 = testGame.getContestant(1);
        assertEquals("Connor", c2.getName());
    }

    @Test
    public void testGetCategoriesNoCategoriesInGame() {
        List<Category> returnCategories = testGame.getCategories();
        assertEquals(0, returnCategories.size());
    }

    @Test
    public void testGetCategoriesManyCategoriesInGame() {
        testGame.addCategory(testCategory1);
        testGame.addCategory(testCategory2);
        List<Category> returnCategories = testGame.getCategories();
        assertEquals(2, returnCategories.size());
        Category c1 = returnCategories.get(0);
        assertEquals("IN ORDER", c1.getName());
        Category c2 = returnCategories.get(1);
        assertEquals("CORPORATE LINGO", c2.getName());
    }

    @Test
    public void testGetContestantsNoContestantsInGame() {
        List<Category> returnContestants = testGame.getCategories();
        assertEquals(0, returnContestants.size());
    }

    @Test
    public void testGetContestantsManyContestantsInGame() {
        testGame.addContestant(testContestant1);
        testGame.addContestant(testContestant2);
        List<Contestant> returnContestants = testGame.getContestants();
        assertEquals(2, returnContestants.size());
        Contestant c1 = returnContestants.get(0);
        assertEquals("Tyler", c1.getName());
        Contestant c2 = returnContestants.get(1);
        assertEquals("Connor", c2.getName());
    }

}
