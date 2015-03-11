package Tests;

import Game.Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MainTest extends Assert {
    HashMap<String, Integer> data= new HashMap<String, Integer>();
    HashMap<String, String> dataSpaces= new HashMap<String, String>();

    @Before
    public void setUp() throws Exception {
        data.put("word 2314", 5);
        data.put("1 sdaf", 2);
        data.put(" 31 ", 1);

        dataSpaces.put("word  word", "word word");
        dataSpaces.put("data  1 2  3", "data 1 2 3");
        dataSpaces.put("  word 2", " word 2");
    }

    @Test
    public void testNextSplitWord() throws Exception {
        for (Map.Entry<String, Integer> entry : data.entrySet() )
        {
            assertEquals(Main.nextSplitWord(entry.getKey()), entry.getValue().intValue());
        }
    }

    @Test
    public void testDeleteSpaces() throws Exception {
        for (Map.Entry<String, String> entry : dataSpaces.entrySet() )
        {
            assertEquals(Main.deleteSpaces(entry.getKey()), entry.getValue());
        }
    }
}
