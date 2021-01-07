package com.view;

import com.Kiosk;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CheckoutView extends AbstractView {
    private JButton cancelButton;
    private JPanel panel1;
    private JButton removeButton;
    private JLabel totalText;
    private JButton payButton;
    private JScrollPane tableScrollPane;
    private JTable itemsTable;

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
    }

    private void initialiseItemsTable(){
        // Setup the items table
        DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "Quantity", "Price (£)"}, 0);
        itemsTable = new JTable(model);
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
    }
}
