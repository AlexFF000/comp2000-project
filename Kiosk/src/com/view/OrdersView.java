package com.view;

import com.Kiosk;
import com.controller.Controller;
import com.model.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class OrdersView extends DataView {
    private JScrollPane scrollPane;
    private JTable table;
    private JButton deliveredButton;

    public OrdersView(Kiosk window){
        setWindow(window);
        initialise();
    }

    @Override
    public void delete() {
        // Ask controller to delete an order
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1){
            String orderId = table.getValueAt(selectedIndex, 0).toString();
            controller.updateModel(Controller.DELETE_ORDER, new JsonObject(new JsonObject.JsonBuilder(orderId)));
        }
    }

    @Override
    public void addToDisplay(String key, Object... values) {
        // Add an order to the table
        if (values.length == 4){
            Object[] row = new Object[5];
            row[0] = key;  // Order ID
            row[1] = values[0];  // Barcode
            row[2] = values[1];  // Quantity
            row[3] = new SimpleDateFormat("dd/MM/yyyy").format(values[2]);  // Date ordered
            row[4] = String.format("%.2f", (float) values[3]);  // Cost
            ((DefaultTableModel) table.getModel()).addRow(row);
            itemsInTable.add(key);
        }
    }

    @Override
    public void removeDisplayedItem(String key) {
        // Remove an order from the table
        int rowIndex = getItemIndex(key);
        if (rowIndex != -1){
            ((DefaultTableModel) table.getModel()).removeRow(rowIndex);
            itemsInTable.remove(rowIndex);
            if (table.getSelectedRow() == -1){
                // If no rows selected, disable buttons
                deliveredButton.setEnabled(false);
                deleteItemButton.setEnabled(false);
            }
        }
    }

    @Override
    protected void initialiseTable(){
        gotoOrdersButton.setEnabled(false);
        // Orders cannot be edited, so hide editButton
        editButton.setVisible(false);
        table = new JTable(new DefaultTableModel(new String[]{"Order ID", "Barcode", "Quantity", "Date Ordered", "Cost (£)"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        scrollPane = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        GridBagConstraints constraints = new GridBagConstraints();
        // Orders cannot be added from this screen, so replace add button with Delivered button
        addItemButton.setVisible(false);
        deliveredButton = new JButton("Delivered");
        deliveredButton.setEnabled(false);
        constraints.weightx = 0;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(deliveredButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        constraints.weighty = 0.9;
        constraints.weightx = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        add(scrollPane, constraints);
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.gridx = 3;
        constraints.gridy = 2;


        deleteItemButton.addActionListener(e -> {
            delete();
        });

        deliveredButton.addActionListener(e -> {
            delivered();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            // Enable delivered and delete buttons when row selected
            deliveredButton.setEnabled(true);
            deleteItemButton.setEnabled(true);
        });
    }

    public void delivered(){
        // Ask controller to mark selected order as delivered
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1){
            String orderId = table.getValueAt(selectedRow, 0).toString();
            controller.updateModel(Controller.ORDER_DELIVERED, new JsonObject(new JsonObject.JsonBuilder(orderId)));
        }
    }
}
