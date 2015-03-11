package Tests;

import Game.Command.Command;
import Game.Factory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest extends Assert {

    Game.Factory factory = new Factory();
    Class testClass;

    @Before
    public void setUp() throws Exception {
        testClass = new TestClass().getClass();
    }

    @Test
    public void testCreateAllCommand() throws Exception {
        String path = new String("Tests/factory.test.properties");

        factory.createAllCommand(path);

        assertEquals(Command.allCommand.get("TEST").getClass(), testClass);
        assertEquals(Command.allCommand.containsKey("WRONG_CLASS"), false);
    }


}
