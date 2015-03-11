package view;

import storage.Storage;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewStorage extends JPanel implements Observer {

    Storage storage;

    JTextField fieldName;

    JTextField fieldCapacity;

    JTextField capacity;

    JTextField fieldCurrent;

    JTextField current;

    JProgressBar progress;

    public ViewStorage(Storage storage, String name) {
        this.storage = storage;

        fieldName = new JTextField(name);

        fieldCapacity = new JTextField("Capacity:");
        capacity = new JTextField(storage.getCapacity().toString());

        fieldCurrent = new JTextField("Current:");

        synchronized (storage) {
            current = new JTextField(storage.getSize());
        }

        progress = new JProgressBar(0, storage.getCapacity());
        this.add(fieldName);

        this.add(fieldCapacity);
        this.add(capacity);

        this.add(fieldCurrent);
        this.add(current);

        this.add(progress);

        this.setLayout(new GridLayout(6,1));
        this.setVisible(true);


    }

    public void update(Observable ob, Object o) {
        Integer currentValue = (Integer) o;

        current.setText(currentValue.toString());
        progress.setValue(currentValue);
    }


}
