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

//source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo - based on JsonReaderTest class
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/jsonReaderTest/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/jsonReaderTest/testReaderEmptyGame.json");
        try {
            Game game = reader.read();
            assertEquals(0, game.numberOfCategories());
            assertEquals(0, game.numberOfContestants());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderContestantsEmpty() {
        JsonReader reader = new JsonReader("./data/jsonReaderTest/testReaderContestantsEmpty.json");
        try {
            Game game = reader.read();
            assertEquals(0, game.numberOfContestants());
            assertEquals(5, game.numberOfCategories());

            List<Category> categories = initializeCategories();
            checkCategories(game, categories);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderCategoriesEmpty() {
        JsonReader reader = new JsonReader("./data/jsonReaderTest/testReaderCategoriesEmpty.json");
        try {
            Game game = reader.read();
            assertEquals(2, game.numberOfContestants());
            assertEquals(0, game.numberOfCategories());

            List<Contestant> contestants = initializeContestants();
            checkContestants(game, contestants);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/jsonReaderTest/testReaderGeneralGame.json");
        try {
            Game game = reader.read();
            assertEquals(2, game.numberOfContestants());
            assertEquals(5, game.numberOfCategories());

            List<Category> categories = initializeCategories();
            checkCategories(game, categories);

            List<Contestant> contestants = initializeContestants();
            checkContestants(game, contestants);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // EFFECTS: returns a list of 2 contestants
    public List<Contestant> initializeContestants() {
        List<Contestant> contestants = new ArrayList<>();
        addContestants(contestants);
        return contestants;
    }

    // MODIFIES: contestants
    // EFFECTS: adds 2 contestants to contestants
    private void addContestants(List<Contestant> contestants) {
        Contestant c1 = createContestant1();
        contestants.add(c1);

        Contestant c2 = createContestant2();
        contestants.add(c2);
    }
}
