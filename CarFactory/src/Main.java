import factory.Factory;
import view.View;

import javax.swing.*;

public class Main {

    static Factory factory;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                factory = new Factory("factory.properties");

                View view = new view.View(factory);
            }
        });

    }
}