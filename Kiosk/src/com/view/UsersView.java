package com.view;

import com.Kiosk;
import com.controller.Controller;
import com.model.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsersView extends DataView {
    private  JScrollPane scrollPane;
    private JTable table;
    public UsersView(Kiosk window){
        setWindow(window);
        initialise();
    }

    @Override
    public void create() {
        // Ask controller to create a new user

        // Display dialog to get item info
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        Object[] fields = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Confirm password:", confirmPasswordField
        };
        int chosenOption = JOptionPane.showConfirmDialog(this, fields , "New User", JOptionPane.OK_CANCEL_OPTION);
        if (chosenOption == JOptionPane.OK_OPTION){
            // Ask controller to create user
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if (password.equals(confirmPassword)){
                    // Only proceed if the passwords match
                    controller.updateModel(Controller.CREATE_USER, new JsonObject(new JsonObject.JsonBuilder(username)
                    .setPassword(password)));
                }
                else{
                    JOptionPane.showMessageDialog(this, "Unable to add new user as passwords didn't match");
                }
            }
            catch (NumberFormatException err){
                JOptionPane.showMessageDialog(this, "Unable to add new user as one the fields was invalid");
            }
        }
    }

    @Override
    public void delete() {
        // Ask controller to delete selected user
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1){
            // Ask user to confirm that they want to delete
            int chosenOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user?", "Delete User", JOptionPane.YES_NO_OPTION);
            if (chosenOption == JOptionPane.YES_OPTION){
                String username = table.getValueAt(selectedIndex, 0).toString();
                controller.updateModel(Controller.DELETE_USER, new JsonObject(new JsonObject.JsonBuilder(username)));
            }
        }
    }

    @Override
    public void update() {
        // Ask controller to modify selected user
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex != -1){
            String currentUsername = table.getValueAt(selectedIndex, 0).toString();
            // Display dialog to get needed info
            JTextField usernameField = new JTextField();
            usernameField.setText(currentUsername);
            JPasswordField passwordField = new JPasswordField();
            JPasswordField confirmPasswordField = new JPasswordField();
            Object[] fields = {
                    "Username:", usernameField,
                    "Password:", passwordField,
                    "Confirm password:", confirmPasswordField
            };
            int chosenOption = JOptionPane.showConfirmDialog(this, fields , "Edit User", JOptionPane.OK_CANCEL_OPTION);
            if (chosenOption == JOptionPane.OK_OPTION){
                // Ask controller to make any necessary modifications

                // If changing both username and password, the password must be changed first as the username is used as the key

                String newUsername = usernameField.getText();
                String newPassword = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                // updateFailed flag will be used to prevent any further modifications if one fails, in order to preserve consistency
                boolean updateFailed = false;
                if (!newPassword.isEmpty()){
                    // Password should be changed
                    if (newPassword.equals(confirmPassword)){
                        controller.updateModel(Controller.UPDATE_PASSWORD, new JsonObject(new JsonObject.JsonBuilder(currentUsername)
                                .setPassword(newPassword)));
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Unable to edit user as passwords didn't match");
                        updateFailed = true;
                    }
                }
                if (!(updateFailed || currentUsername.equals(newUsername))){
                    // Username should also be changed
                    controller.updateModel(Controller.UPDATE_USERNAME, new JsonObject(new JsonObject.JsonBuilder(currentUsername)
                    .setNewKey(newUsername)));
                }
                JOptionPane.showMessageDialog(this, "User details changed");
            }
        }
    }

    @Override
    public void addToDisplay(String key, Object... values) {
        // Add a new row to the table
        Object[] row = {key};
        ((DefaultTableModel) table.getModel()).addRow(row);
        itemsInTable.add(key);
    }

    @Override
    public void editDisplayedItem(String key, String fieldToChange, Object newValue) {
        // Change the displayed username
        int rowIndex = getItemIndex(key);
        if (rowIndex != -1) {
            if (fieldToChange.equals("username")) {
                table.setValueAt(newValue, rowIndex, 0);
                itemsInTable.set(rowIndex, newValue.toString());
            }
        }
    }

    @Override
    public void removeDisplayedItem(String key) {
        // Remove a user from the table
        int rowIndex = getItemIndex(key);
        if (rowIndex != -1){
            ((DefaultTableModel) table.getModel()).removeRow(rowIndex);
            itemsInTable.remove(rowIndex);
            if (table.getSelectedRow() == -1){
                deleteItemButton.setEnabled(false);
                editButton.setEnabled(false);
            }
        }
    }

    @Override
    protected void initialiseTable(){
        gotoUsersButton.setEnabled(false);
        table = new JTable(new DefaultTableModel(new String[]{"Username"}, 0){
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
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.gridx = 3;
        constraints.gridy = 2;

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
            // Enable delete and edit buttons when row selected
            editButton.setEnabled(true);
            deleteItemButton.setEnabled(true);
        });
    }
}
