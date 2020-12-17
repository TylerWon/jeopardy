package persistence;

import model.Category;
import model.Contestant;
import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo - based on JsonWriterTest class
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            Game game = new Game();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyGame() {
        try {
            Game game = new Game();
            JsonWriter writer = new JsonWriter("./data/jsonWriterTest/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/jsonWriterTest/testWriterEmptyGame.json");
            game = reader.read();
            assertEquals(0, game.numberOfContestants());
            assertEquals(0, game.numberOfCategories());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterContestantsEmpty() {
        try {
            Game game = new Game();
            List<Category> categories = initializeCategories(game);
            JsonWriter writer = new JsonWriter("./data/jsonWriterTest/testWriterContestantsEmpty.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/jsonWriterTest/testWriterContestantsEmpty.json");
            game = reader.read();
            assertEquals(0, game.numberOfContestants());
            assertEquals(5, game.numberOfCategories());

            checkCategories(game, categories);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterCategoriesEmpty() {
        try {
            Game game = new Game();
            List<Contestant> contestants = initializeContestants(game);
            JsonWriter writer = new JsonWriter("./data/jsonWriterTest/testWriterCategoriesEmpty.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/jsonWriterTest/testWriterCategoriesEmpty.json");
            game = reader.read();
            assertEquals(2, game.numberOfContestants());
            assertEquals(0, game.numberOfCategories());

            checkContestants(game, contestants);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralGame() {
        try {
            Game game = new Game();
            List<Category> categories = initializeCategories(game);
            List<Contestant> contestants = initializeContestants(game);
            JsonWriter writer = new JsonWriter("./data/jsonWriterTest/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/jsonWriterTest/testWriterGeneralGame.json");
            game = reader.read();
            assertEquals(2, game.numberOfContestants());
            assertEquals(5, game.numberOfCategories());

            checkContestants(game, contestants);
            checkCategories(game, categories);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: returns a list of 5 categories with questions
    private List<Category> initializeCategories(Game game) {
        List<Category> categories = new ArrayList<>();
        addCategories(game, categories);
        return categories;
    }

    // MODIFIES: game
    // EFFECTS: adds 5 categories to game and adds questions to those categories
    private void addCategories(Game game, List<Category> categories) {
        Category c1 = new Category("BILLBOARD TOP 40");
        game.addCategory(c1);
        categories.add(c1);
        addBillboardTop40Questions(c1);

        Category c2 = new Category("THE DOCTOR WILL C YOU NOW");
        game.addCategory(c2);
        categories.add(c2);
        addTheDoctorWillCYouNowQuestions(c2);

        Category c3 = new Category("NUMBERS OF THINGS");
        game.addCategory(c3);
        categories.add(c3);
        addNumbersOfThingsQuestion(c3);

        Category c4 = new Category("IN ORDER");
        game.addCategory(c4);
        categories.add(c4);
        addInOrderQuestions(c4);

        Category c5 = new Category("CORPORATE LINGO");
        game.addCategory(c5);
        categories.add(c5);
        addCorporateLingoQuestions(c5);
    }

    // EFFECTS: returns a list of 2 contestants
    public List<Contestant> initializeContestants(Game game) {
        List<Contestant> contestants = new ArrayList<>();
        addContestants(game, contestants);
        return contestants;
    }

    // MODIFIES: game
    // EFFECTS: adds contestants to game
    private void addContestants(Game game, List<Contestant> contestants) {
        Contestant c1 = createContestant1();
        game.addContestant(c1);
        contestants.add(c1);

        Contestant c2 = createContestant2();
        game.addContestant(c2);
        contestants.add(c2);
    }
}
