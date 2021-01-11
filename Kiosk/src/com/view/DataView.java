package com.view;

import javax.swing.*;
import java.awt.*;

public abstract class DataView extends AbstractView{
    protected JButton logoutButton;
    protected JButton gotoStockButton;
    protected JButton gotoOrdersButton;
    protected JButton gotoUsersButton;
    protected JButton addItemButton;
    protected JButton deleteItemButton;
    protected JButton cancelChangesButton;
    protected JButton saveChangesButton;

    public abstract void create();
    public abstract void delete();
    public abstract void update();
    public abstract void addToDisplay(String key, Object...values);
    public abstract void editDisplayedItem(String key, String fieldToChange, Object newValue);
    public abstract void removeDisplayedItem(String key);
    protected abstract void initialiseTable();

    public void initialise(){
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Add navigation buttons
        constraints.weighty = 0.1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(gotoStockButton, constraints);
        constraints.gridx = 1;
        add(gotoOrdersButton, constraints);
        constraints.gridx = 2;
        add(gotoUsersButton, constraints);
        // Add logout button
        constraints.gridx = 4;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        constraints.weightx = 0;
        add(logoutButton, constraints);

        // Add data manipulation buttons
        constraints.weightx = 0;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(addItemButton, constraints);
        constraints.gridx = 1;
        add(deleteItemButton, constraints);
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.gridx = 3;
        constraints.weightx = 0.1;
        add(cancelChangesButton, constraints);
        constraints.weightx = 0;
        constraints.gridx = 4;
        add(saveChangesButton, constraints);

        // Initialise table using sub class implementation
        initialiseTable();
    }
}
