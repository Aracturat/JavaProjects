package Tests;

import Game.Command.Command;

import java.util.ArrayList;

public class TestClass extends Command {

    public TestClass() {
        super.numOfParams = 0;
        super.description = "\t\tTest command";
    }

    public boolean exec(ArrayList<String> param) {
        //Check number of parameter and board initialization.
        if (false == super.check(param.size())) {
            return false;
        }

        return true;
    }
}
