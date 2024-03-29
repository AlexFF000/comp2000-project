package com.view;

import com.Kiosk;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class DataView extends AbstractView{
    protected JButton logoutButton;
    protected JButton gotoStockButton;
    protected JButton gotoOrdersButton;
    protected JButton gotoUsersButton;
    protected JButton addItemButton;
    protected JButton deleteItemButton;
    protected JButton cancelChangesButton;
    protected JButton editButton;
    // List of keys to store the index of each item in the table
    protected ArrayList<String> itemsInTable;

    // Create a new model item
    public void create(){};
    // Delete a model item
    public abstract void delete();
    // Update a model item
    public void update(){};
    // Add a model item to the display
    public abstract void addToDisplay(String key, Object...values);
    // Edit displayed details of a model item
    public void editDisplayedItem(String key, String fieldToChange, Object newValue){};
    // Remove a model item from the display
    public abstract void removeDisplayedItem(String key);
    // Create the table for displaying the items
    protected abstract void initialiseTable();

    public void initialise(){
        itemsInTable = new ArrayList<>();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Add navigation buttons
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0.1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(gotoStockButton, constraints);
        constraints.fill = GridBagConstraints.NONE;
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
        constraints.weightx = 0.1;
        constraints.gridx = 2;
        add(editButton, constraints);

        logoutButton.addActionListener(e ->{
            window.switchView(Kiosk.START_VIEW);
        });

        gotoStockButton.addActionListener(e -> {
            window.switchView(Kiosk.STOCK_VIEW);
        });
        gotoOrdersButton.addActionListener(e -> {
            window.switchView(Kiosk.ORDERS_VIEW);
        });
        gotoUsersButton.addActionListener(e -> {
            window.switchView(Kiosk.USERS_VIEW);
        });

        // Initialise table using sub class implementation
        initialiseTable();
    }

    protected int getItemIndex(String key){
        // Get index of row in table that has the given key, or return -1 if it isn't present
        for (int i = 0; i < itemsInTable.size(); i++){
            if (itemsInTable.get(i).equals(key)) return i;
        }
        return -1;
    }
}
