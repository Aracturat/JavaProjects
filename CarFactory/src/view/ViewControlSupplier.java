package view;

import suppliers.SupplierGroup;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.LinkedList;

public class ViewControlSupplier extends JPanel {

    final SupplierGroup suppliers;

    JTextField fieldCurrentDelay;

    JTextField currentDelay;

    JSlider slider;

    public ViewControlSupplier(SupplierGroup group) {
        this.setVisible(true);

        suppliers = group;

        fieldCurrentDelay = new JTextField();

        currentDelay = new JTextField();

        slider = new JSlider(0,10);
        slider.setValue(0);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                suppliers.setDelay(slider.getValue()*1000);
            }
        });

        this.add(slider);
    }



}
