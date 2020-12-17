package persistence;

import model.Category;
import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo - based on JsonReaderTest class
public class QuestionsReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        QuestionsReader reader = new QuestionsReader("./data/questionsReaderTest/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyGame() {
        QuestionsReader reader = new QuestionsReader("./data/questionsReaderTest/testReaderCategoriesEmpty.json");
        try {
            Game game = reader.read();
            assertEquals(5, game.numberOfCategories());
            assertEquals(0, game.numberOfContestants());

            List<Category> categories = initializeCategories();
            checkCategories(game, categories);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralGame() {
        QuestionsReader reader = new QuestionsReader("./data/questionsReaderTest/testReaderGeneralGame.json");
        try {
            Game game = reader.read();
            assertEquals(5, game.numberOfCategories());
            assertEquals(0, game.numberOfContestants());

            List<Category> categories = initializeCategories();
            checkCategories(game, categories);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
