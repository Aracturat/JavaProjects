package tests;

import model.Scores;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScoresTest extends Assert {

    Scores scores;

    @Before
    public void setUp() throws Exception {
        scores = new Scores("/test_scores.properties");
    }

    @Test
    public void testAddScore() throws Exception {
        scores.addScore("LEVEL1", "test", 40);

        assertEquals(scores.getScore("LEVEL1").getPlayerName(), "test");
        assertEquals(scores.getScore("LEVEL1").getNumberOfStep(), 40);
    }

    @Test
    public void testAddLowScore() throws Exception {
        scores.addScore("LEVEL2", "test", 40);
        scores.addScore("LEVEL2", "test", 50);

        assertEquals(scores.getScore("LEVEL2").getPlayerName(), "test");
        assertEquals(scores.getScore("LEVEL2").getNumberOfStep(), 40);
    }

    @Test
    public void testAddHighScore() throws Exception {
        scores.addScore("LEVEL3", "test", 40);
        scores.addScore("LEVEL3", "test1", 10);
        assertEquals(scores.getScore("LEVEL3").getPlayerName(), "test1");
        assertEquals(scores.getScore("LEVEL3").getNumberOfStep(), 10);
    }
}
