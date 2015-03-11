package tests;

import model.LevelFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LevelFactoryTest extends Assert {

    LevelFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new LevelFactory();
    }

    @Test
    public void testCreateLevel() throws Exception {
        assertNull(factory.createLevel("lv"));
        assertNotNull(factory.createLevel("levels/lv1"));
    }
}
