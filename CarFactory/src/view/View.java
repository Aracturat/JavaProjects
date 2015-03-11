package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import factory.Factory;

public class View extends JFrame {

    Factory factory;

    JButton start;

    ViewStorage viewStorageCar;

    ViewStorage viewStorageBody;

    ViewStorage viewStorageEngine;

    ViewStorage viewStorageAccessory;

    ViewControlSupplier viewControlDealer;

    ViewControlSupplier viewControlEngine;

    ViewControlSupplier viewControlBody;

    ViewControlSupplier viewControlAccessory;

    JPanel control;

    JPanel view;

    public View(Factory factory) {
        super("Factory");

        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.factory = factory;

        this.setStorage();
        this.add(view);

        this.setController();
        this.add(control);

        this.add(getButtonStart());

        this.setLayout(new GridLayout(3,1));
        this.pack();
    }

    private JButton getButtonStart() {
        if (null == start) {
            start = new JButton("Start");
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    factory.start();
                }
            });
        }
        return start;
    }

    private void setStorage() {
        view = new JPanel();
        view.setLayout(new GridLayout(1,4));

        viewStorageCar = new ViewStorage(factory.getStorageByName("Car"), "Car");
        viewStorageEngine = new ViewStorage(factory.getStorageByName("Engine"), "Engine");
        viewStorageBody = new ViewStorage(factory.getStorageByName("Body"), "Body");
        viewStorageAccessory = new ViewStorage(factory.getStorageByName("Accessory"), "Accessory");

        factory.getStorageByName("Car").addObserver(viewStorageCar);
        factory.getStorageByName("Body").addObserver(viewStorageBody);
        factory.getStorageByName("Engine").addObserver(viewStorageEngine);
        factory.getStorageByName("Accessory").addObserver(viewStorageAccessory);

        view.add(viewStorageCar);
        view.add(viewStorageAccessory);
        view.add(viewStorageBody);
        view.add(viewStorageEngine);
    }

    private void setController() {
        control = new JPanel();
        control.setLayout(new GridLayout(1,4));

        viewControlAccessory = new ViewControlSupplier(factory.getSuppliersByName("Accessory"));
        viewControlBody = new ViewControlSupplier(factory.getSuppliersByName("Body"));
        viewControlEngine = new ViewControlSupplier(factory.getSuppliersByName("Engine"));
        viewControlDealer = new ViewControlSupplier(factory.getSuppliersByName("Car"));

        control.add(viewControlDealer);
        control.add(viewControlAccessory);
        control.add(viewControlBody);
        control.add(viewControlEngine);
    }
}
