package com.view;

import com.Kiosk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StockView extends DataView {
    private JScrollPane scrollPane;
    private JTable table;
    private JButton reorderButton;
    public StockView(Kiosk window){
        setWindow(window);
        initialise();
    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public void addToDisplay(String key, Object... values) {

    }

    @Override
    public void editDisplayedItem(String key, String fieldToChange, Object newValue) {

    }

    @Override
    public void removeDisplayedItem(String key) {

    }

    @Override
    protected void initialiseTable(){
        gotoStockButton.setEnabled(false);
        table = new JTable(new DefaultTableModel(new String[]{"Barcode", "Name", "Quantity in Stock", "Reorder Level", "Sale Price (£)", "Supplier Price (£)"}, 0));
        scrollPane = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        constraints.weighty = 0.9;
        constraints.weightx = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        add(scrollPane, constraints);
        // Add reorder button
        reorderButton = new JButton("Reorder");
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 2;
        add(reorderButton, constraints);
    }
}
