package persistence;

import model.Category;
import model.Contestant;
import model.Game;
import model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonTest {

    // EFFECTS: returns a list of 5 categories with questions
    protected List<Category> initializeCategories() {
        List<Category> categories = new ArrayList<>();
        addCategories(categories);
        return categories;
    }

    // MODIFIES: categories
    // EFFECTS: adds 5 categories to categories and adds questions to those categories
    protected void addCategories(List<Category> categories) {
        Category c1 = new Category("BILLBOARD TOP 40");
        categories.add(c1);
        addBillboardTop40Questions(c1);

        Category c2 = new Category("THE DOCTOR WILL C YOU NOW");
        categories.add(c2);
        addTheDoctorWillCYouNowQuestions(c2);

        Category c3 = new Category("NUMBERS OF THINGS");
        categories.add(c3);
        addNumbersOfThingsQuestion(c3);

        Category c4 = new Category("IN ORDER");
        categories.add(c4);
        addInOrderQuestions(c4);

        Category c5 = new Category("CORPORATE LINGO");
        categories.add(c5);
        addCorporateLingoQuestions(c5);
    }

    // MODIFIES: this
    // EFFECTS: adds questions to c corresponding to the category Billboard Top 40
    protected void addBillboardTop40Questions(Category c) {
        c.addToUnansweredQuestions(new Question(
                "Madonna touched No. 1 on the Billboard Top 40 for the very first time with this 1984 song",
                "like a virgin",
                200,
                false));

        c.addToUnansweredQuestions(new Question(
                "Hey Ya! by this duo spent about 30 weeks in the Top 40 in 2003 & 2004",
                "outkast",
                400,
                false));

        c.addToUnansweredQuestions(new Question(
                "Rockstar by him featuring Roddy Ricch topped the Billboard charts in July 2020",
                "dababy",
                600,
                false));

        c.addToUnansweredQuestions(new Question(
                "ABBA had 14 songs on the Billboard chart but only this disco-themed one made it to No. 1",
                "dancing queen",
                800,
                false));

        c.addToUnansweredQuestions(new Question(
                "Weird Al Yankovic made a foray into the Top 40 with this Michael Jackson parody",
                "eat it",
                1000,
                false));
    }

    // MODIFIES: this
    // EFFECTS: adds questions to c corresponding to the category The Doctor Will C You Now
    protected void addTheDoctorWillCYouNowQuestions(Category c) {
        c.addToUnansweredQuestions(new Question(
                "Christmas must be a bummer for you having this condition & not being able to distinguish between red & green",
                "colorblindness",
                200,
                false));

        c.addToUnansweredQuestions(new Question(
                "Try stretching if you suffer these like Rafael Nadal did at the 2011 U.S. Open including at a press conference",
                "cramps",
                400,
                false));

        c.addToUnansweredQuestions(new Question(
                "What's that I say? You need this type of implant in your inner ear that directly stimulates the hearing nerve",
                "a cochlear implant",
                600,
                true));

        c.addToUnansweredQuestions(new Question(
                "Found in the liver it helps make vitamin D; hope you have a lot more good than bad!",
                "cholesterol",
                800,
                false));

        c.addToUnansweredQuestions(new Question(
                "When looking at areas of thickened skin on your feet these are larger & more shapeless than corns",
                "calluses",
                1000,
                false));
    }

    // MODIFIES: this
    // EFFECTS: adds questions to c corresponding to the category Numbers of Things
    protected void addNumbersOfThingsQuestion(Category c) {
        c.addToUnansweredQuestions(new Question(
                "Added in 1971 No. 26 of the 27 amendments to the Constitution set this at 18",
                "the voting age",
                200,
                false));

        c.addToUnansweredQuestions(new Question(
                "The 9 Greek goddesses known as Muses included Melpomene & Thalia patrons of these 2 types of drama symbolized by masks",
                "tragedy and comedy",
                400,
                false));

        c.addToUnansweredQuestions(new Question(
                "This septet includes the Mausoleum at Halicarnassus",
                "the seven wonders of the world",
                600,
                false));

        c.addToUnansweredQuestions(new Question(
                "It's the number of squares on a checkerboard",
                "64",
                800,
                false));

        c.addToUnansweredQuestions(new Question(
                "Hesiod said this beast had 50 heads not 3 & Hercules' final labor of 12 was to bring it from the underworld up to the surface",
                "Cerberus",
                1000,
                false));
    }

    // MODIFIES: this
    // EFFECTS: adds questions to c corresponding to the category In order
    protected void addInOrderQuestions(Category c) {
        c.addToUnansweredQuestions(new Question(
                "Someone who accomplishes things",
                "a doer",
                200,
                false));

        c.addToUnansweredQuestions(new Question(
                "Fish ova",
                "roe",
                400,
                false));

        c.addToUnansweredQuestions(new Question(
                "Morgan Freeman played him in The Shawshank Redemption",
                "red",
                600,
                false));

        c.addToUnansweredQuestions(new Question(
                "Chalcocite is one",
                "an ore",
                800,
                false));

        c.addToUnansweredQuestions(new Question(
                "A canal links this river with the Vistula",
                "the oder",
                1000,
                true));
    }

    // MODIFIES: this
    // EFFECTS: adds questions to c corresponding to the category Coporate Lingo
    protected void addCorporateLingoQuestions(Category c) {
        c.addToUnansweredQuestions(new Question(
                "In baseball you do this to avoid being thrown out as a runner; in meetings it means to follow up with a person",
                "touch base",
                200,
                false));

        c.addToUnansweredQuestions(new Question(
                "In biology it's all the diverse organisms in a location; in business it's all the parts of an industry",
                "an ecosystem",
                400,
                false));

        c.addToUnansweredQuestions(new Question(
                "You can do this to change songs on a record or in business to generate a desired reaction or change the outcome",
                "move the needle",
                600,
                false));

        c.addToUnansweredQuestions(new Question(
                "A printing term for an image that pushes past the margin is this edge more innovative than cutting edge",
                "bleeding edge",
                800,
                true));

        c.addToUnansweredQuestions(new Question(
                "This adjective describes a mountain that can be climbed or a company that can handle increased sales or workload",
                "scalable",
                1000,
                false));
    }

    // EFFECTS: checks that each category in game is the expected category
    protected void checkCategories(Game game, List<Category> categories) {
        List<Category> gameCategories = game.getCategories();

        // iterate through each Category in the Game

        // since categories are added in a random order to Game, we check the gameCategory against each checkCategory
        // if the gameCategory and checkCategory match, we check their questions and break out of loop to move onto next Category
        // otherwise, we add one to count and continue checking against each checkCategory

        // if we iterate through the entire list of expected categories (i.e. count = 5), something is wrong so fail
        // otherwise we move onto the next gameCategory to check
        int count = 0;
        for (Category gameCategory : gameCategories) {
            for (Category checkCategory : categories) {
                if (gameCategory.equals(checkCategory)) {
                    checkQuestions(gameCategory, checkCategory);
                    break;
                }
                count++;
            }
            if (count == 5) {
                fail();
            }
            count = 0;
        }
    }

    // EFFECTS: checks that each question is the expected question
    protected void checkQuestions(Category gameCategory, Category checkCategory) {
        List<Question> gameQuestions = gameCategory.getUnansweredQuestions();
        List<Question> checkQuestions = checkCategory.getUnansweredQuestions();

        int index = 0;
        for (Question gameQuestion : gameQuestions) {
            assertTrue(gameQuestion.equals(checkQuestions.get(index)));
            index++;
        }
    }

    // EFFECTS: returns a pre-made contestant
    protected Contestant createContestant1() {
        Contestant c1 = new Contestant("Tyler", 0, 0, 0, new HashMap<>());
        return c1;
    }

    // EFFECTS: returns a pre-made contestant
    protected Contestant createContestant2() {
        Map<String, Integer> categoriesPicked = new HashMap<>();
        categoriesPicked.put("IN ORDER", 1);
        categoriesPicked.put("CORPORATE LINGO", 2);

        Contestant c2 = new Contestant("Connor", 1000, 2, 2, categoriesPicked);
        return c2;
    }

    // EFFECTS: checks that each contestant in game is the expected contestant
    protected void checkContestants(Game game, List<Contestant> contestants) {
        List<Contestant> gameContestants = game.getContestants();

        int index = 0;
        for (Contestant gameContestant : gameContestants) {
            assertTrue(gameContestant.equals(contestants.get(index)));
            index++;
        }
    }
}
