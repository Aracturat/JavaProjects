package Tests;

import Game.Board;
import Game.Command.Command;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class CommandTest extends Assert {
    Command testClass;
    ArrayList<String> param = new ArrayList<String>();
    Board board = new Board();

    @Before
    public void setUp() throws Exception {
        Command.setBoard(board);
        testClass = new TestClass();
        param.add("1");
    }

    @Test
    public void testExec() throws Exception {
        assertFalse(testClass.exec(param));

        param.clear();
        assertFalse(testClass.exec(param));

        board.setInit();
        assertTrue(testClass.exec(param));
    }
}
