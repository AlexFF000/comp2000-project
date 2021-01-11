package com.view;

import com.Kiosk;
import com.controller.Controller;
import com.controller.InventoryController;
import com.model.JsonObject;

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
        JTextField barcodeField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField reorderLvlField = new JTextField();
        JTextField salePriceField = new JTextField();
        JTextField supplierPriceField = new JTextField();
        Object[] fields = {
                "Barcode:", barcodeField,
                "Name:", nameField,
                "Quantity:", quantityField,
                "Reorder level:", reorderLvlField,
                "Sale price:", salePriceField,
                "Supplier price:", supplierPriceField
        };

        int chosenOption = JOptionPane.showConfirmDialog(this, fields , "New Item", JOptionPane.OK_CANCEL_OPTION);
        if (chosenOption == JOptionPane.OK_OPTION){
            try {
                String barcode = barcodeField.getText();
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                int reorderLvl = Integer.parseInt(reorderLvlField.getText());
                // First remove any characters that are not either . or digits (to remove any currency symbols)
                float salePrice = Float.parseFloat(salePriceField.getText().replaceAll("[^0-9.]", ""));
                float supplierPrice = Float.parseFloat(supplierPriceField.getText().replaceAll("[^0-9.]", ""));
                // Create JsonObject for new item
                JsonObject newItem = new JsonObject(new JsonObject.JsonBuilder(barcode)
                        .setName(name)
                        .setQuantityInStock(quantity)
                        .setReorderLevel(reorderLvl)
                        .setSalePrice(salePrice)
                        .setSupplierPrice(supplierPrice));
                // Ask controller to create new item
                controller.updateModel(Controller.CREATE_STOCKITEM, newItem);
            }
            catch (NumberFormatException err){
                JOptionPane.showMessageDialog(this, "Unable to add new item as one the fields was invalid");
            }
        }
    }

    @Override
    public void delete() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1){
            String barcode = table.getValueAt(selectedIndex, 0).toString();
            controller.updateModel(Controller.DELETE_STOCKITEM, new JsonObject(new JsonObject.JsonBuilder(barcode)));
        }
    }

    @Override
    public void update() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1){
            String currentBarcode = table.getValueAt(selectedIndex, 0).toString();
            String currentName = table.getValueAt(selectedIndex, 1).toString();
            String currentQuantity = table.getValueAt(selectedIndex, 2).toString();
            String currentReorderLvl = table.getValueAt(selectedIndex, 3).toString();
            String currentSalePrice = table.getValueAt(selectedIndex, 4).toString();
            String currentSupplierPrice = table.getValueAt(selectedIndex, 5).toString();
            JTextField barcodeField = new JTextField();
            barcodeField.setText(currentBarcode);
            JTextField nameField = new JTextField();
            nameField.setText(currentName);
            JTextField quantityField = new JTextField();
            quantityField.setText(currentQuantity);
            JTextField reorderLvlField = new JTextField();
            reorderLvlField.setText(currentReorderLvl);
            JTextField salePriceField = new JTextField();
            salePriceField.setText(currentSalePrice);
            JTextField supplierPriceField = new JTextField();
            supplierPriceField.setText(currentSupplierPrice);
            Object[] fields = {
                    "Barcode:", barcodeField,
                    "Name:", nameField,
                    "Quantity:", quantityField,
                    "Reorder level:", reorderLvlField,
                    "Sale price:", salePriceField,
                    "Supplier price:", supplierPriceField
            };

            int chosenOption = JOptionPane.showConfirmDialog(this, fields , "Edit Item", JOptionPane.OK_CANCEL_OPTION);
            if (chosenOption == JOptionPane.OK_OPTION){
                try {
                    // Ask controller to update any items that have been changed
                    if (!barcodeField.getText().equals(currentBarcode)){
                        controller.updateModel(Controller.UPDATE_ITEM_BARCODE, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                        .setNewKey(barcodeField.getText())));
                    }
                    if (!nameField.getText().equals(currentName)){
                        controller.updateModel(Controller.UPDATE_ITEM_NAME, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                        .setName(nameField.getText())));
                    }
                    if (!quantityField.getText().equals(currentQuantity)){
                        int newQuantity = Integer.parseInt(quantityField.getText());
                        controller.updateModel(Controller.UPDATE_ITEM_QUANTITY, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                        .setQuantityInStock(newQuantity)));
                    }
                    if (!reorderLvlField.getText().equals(currentReorderLvl)){
                        int newLevel = Integer.parseInt(reorderLvlField.getText());
                        controller.updateModel(Controller.UPDATE_REORDER_LEVEL, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                        .setReorderLevel(newLevel)));
                    }
                    String priceFieldValue = salePriceField.getText().replaceAll("[^0-9.]", "");
                    if (!priceFieldValue.equals(currentSalePrice)){
                        float newPrice = Float.parseFloat(priceFieldValue);
                        controller.updateModel(Controller.UPDATE_SALE_PRICE, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                        .setSalePrice(newPrice)));
                    }
                    priceFieldValue = supplierPriceField.getText().replaceAll("[^0-9.]", "");
                    if (!priceFieldValue.equals(currentSupplierPrice)){
                        float newPrice = Float.parseFloat(priceFieldValue);
                        controller.updateModel(Controller.UPDATE_SUPPLIER_PRICE, new JsonObject(new JsonObject.JsonBuilder(currentBarcode)
                                .setSalePrice(newPrice)));
                    }
                }
                catch (NumberFormatException err){
                    JOptionPane.showMessageDialog(this, "Unable to modify item as one the fields was invalid");
                }
            }
        }

    }

    @Override
    public void addToDisplay(String key, Object... values) {
        if (values.length == 5){
            Object[] row = new Object[6];
            row[0] = key;  // Barcode
            row[1] = values[0];  // Name
            row[2] = values[1];  // Quantity
            row[3] = values[2];  // Reorder level
            row[4] = String.format("%.2f", (float) values[3]);  // Sale price
            row[5] = String.format("%.2f", (float) values[4]);  // Supplier price
            ((DefaultTableModel) table.getModel()).addRow(row);
        }
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
        table = new JTable(new DefaultTableModel(new String[]{"Barcode", "Name", "Quantity in Stock", "Reorder Level", "Sale Price (£)", "Supplier Price (£)"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
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
        constraints.gridx = 3;
        constraints.gridy = 2;
        reorderButton.setEnabled(false);
        add(reorderButton, constraints);

        addItemButton.addActionListener(e -> {
            create();
        });

        deleteItemButton.addActionListener(e -> {
            delete();
        });

        editButton.addActionListener(e -> {
            update();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            // Enable delete, edit, and reorder buttons when row selected
            editButton.setEnabled(true);
            deleteItemButton.setEnabled(true);
            reorderButton.setEnabled(true);
        });


    }
}
