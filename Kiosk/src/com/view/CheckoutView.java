package com.view;

import com.Kiosk;
import com.controller.CheckoutController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class CheckoutView extends AbstractView implements IBarcodeScannerObserver{
    private JButton cancelButton;
    private JPanel panel1;
    private JButton removeButton;
    private JLabel totalText;
    private JButton payButton;
    private JScrollPane tableScrollPane;
    private JTable itemsTable;
    // List of barcodes to store the index of each barcode in the table
    private ArrayList<String> barcodesInTable;


    public CheckoutView(Kiosk window){
        setWindow(window);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        // Add items table
        initialiseItemsTable();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.gridheight = 5;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(tableScrollPane, constraints);
        // Add cancel button
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.1;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(cancelButton, constraints);
        // Add remove button
        constraints.ipadx = 0;
        constraints.gridx = 1;
        constraints.gridy = 6;
        add(removeButton, constraints);
        add(Box.createGlue(), constraints);
        // Add totalText
        constraints.gridx = 2;
        constraints.gridy = 6;
        add(totalText, constraints);
        // Add pay button
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.weightx = 0.1;
        constraints.weighty = 0.2;
        add(payButton, constraints);
        // Add event listeners
        removeButton.addActionListener(e -> {
            // Remove the selected item from the basket
            int rowIndex = itemsTable.getSelectedRow();
            String barcode = barcodesInTable.get(rowIndex);
            if (controller.getClass() == CheckoutController.class){
                ((CheckoutController) controller).removeScannedItem(barcode);
            }
        });
        cancelButton.addActionListener(e -> cancel());
        payButton.addActionListener(e -> {
            // Switch to payment screen
            window.switchView(Kiosk.PAYMENT_VIEW);
        });
    }

    private void initialiseItemsTable(){
        // Setup the items table
        barcodesInTable = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Quantity", "Price (£)"}, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                // Override isCellEditable to make all cells read only
                return false;
            }
        };
        itemsTable = new JTable(model);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        tableScrollPane = new JScrollPane(itemsTable);
        itemsTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Resize columns when table resized so that item name column always uses 70% of width, and quantity and price each use 15%
                super.componentResized(e);
                int newTableWidth = itemsTable.getWidth();
                TableColumnModel columns = itemsTable.getColumnModel();
                columns.getColumn(0).setPreferredWidth((int)(newTableWidth * 0.7));
                columns.getColumn(1).setPreferredWidth((int)(newTableWidth * 0.15));
                columns.getColumn(2).setPreferredWidth((int)(newTableWidth * 0.15));
            }
        });

        itemsTable.getSelectionModel().addListSelectionListener(
                e -> {
                    // Enable remove button when a row is selected
                    removeButton.setEnabled(true);
                }
        );
    }

    public void addItemToDisplay(String barcode, String name, float price){
        int rowIndex = getBarcodeIndex(barcode);
        if (rowIndex == -1){
            // No items with that barcode have been added yet
            String priceString = String.format("%.2f", price);
            Object[] row = new Object[]{name, 1, priceString};
            ((DefaultTableModel) itemsTable.getModel()).addRow(row);
            barcodesInTable.add(barcode);
            payButton.setEnabled(true);
        }
        else {
            // An item with that barcode has already been added, so just increment the quantity
            DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
            int currentQuantity = (int) model.getValueAt(rowIndex, 1);
            currentQuantity++;
            model.setValueAt(currentQuantity, rowIndex, 1);
        }
    }

    public void removeItemFromDisplay(String barcode){
        int rowIndex = getBarcodeIndex(barcode);
        if (rowIndex != -1){
            ((DefaultTableModel)itemsTable.getModel()).removeRow(rowIndex);
            barcodesInTable.remove(barcode);
            if (barcodesInTable.isEmpty()){
                payButton.setEnabled(false);
            }
        }
    }

    public void setTotal(float total){
        String text = String.format("Total: £%.2f", total);
        totalText.setText(text);
    }

    private int getBarcodeIndex(String barcode){
        // Get index of table containing barcode (or -1 if it isn't in the table)
        for (int i = 0; i < barcodesInTable.size(); i++){
            if (barcodesInTable.get(i).equals(barcode)) return i;
        }
        return -1;
    }

    private void cancel(){
        if (controller.getClass() == CheckoutController.class){
            ((CheckoutController) controller).cancel();
        }
        window.switchView(Kiosk.START_VIEW);
    }

    @Override
    public void useBarcode(String barcode){
        // Take a barcode input from barcode scanner
        Class controllerClass = controller.getClass();
        if (controllerClass == CheckoutController.class){
            // Only continue if controller is a CheckoutController
            ((CheckoutController) controller).scanItem(barcode);
        }
    }
}
