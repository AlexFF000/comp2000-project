package com.view;

import com.Kiosk;
import com.controller.UsersController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends AbstractView{
    private JTextField usernameField;
    private JPanel panel1;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean usernameEntered;
    private boolean passwordEntered;

    public LoginView(Kiosk window){
        setWindow(window);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Add empty space at top
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        add(Box.createGlue(), constraints);
        // Add username field
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        add(usernameField, constraints);
        // Add empty space between username and password fields
        constraints.gridy = 2;
        constraints.ipady = usernameField.getPreferredSize().height;
        add(Box.createGlue(), constraints);
        // Add password field
        constraints.ipady = 0;
        constraints.gridy = 3;
        add(passwordField, constraints);
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        add(loginButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.weighty = 0.1;
        add(cancelButton, constraints);
        // Add empty space on right
        constraints.gridx = 2;
        add(Box.createGlue(), constraints);

        // When nothing has been entered, text fields should contain "username" and "password"
        usernameEntered = false;
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (!usernameEntered){
                    usernameField.setText("");
                    usernameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                    usernameEntered = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e){
                super.focusLost(e);
                if (usernameField.getText().equals("")){
                    usernameField.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
                    usernameField.setText("Username");
                    usernameEntered = false;
                }
            }
        });
        // Show password text
        passwordField.setEchoChar((char) 0);
        passwordEntered = false;
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (!passwordEntered){
                    passwordField.setText("");
                    passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                    // Hide password text
                    passwordField.setEchoChar((char) 8226);
                    passwordEntered = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0){
                    passwordField.setText("Password");
                    passwordField.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
                    passwordField.setEchoChar((char) 0);
                    passwordEntered = false;
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usernameEntered){
                    usernameField.setText("Please enter a username");
                }
                if (!passwordEntered){
                    passwordField.setText("Please enter a password");
                }
                if (usernameEntered && passwordEntered){
                    // Both fields provided, so check credentials
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    if (controller.getClass() == UsersController.class){
                        ((UsersController) controller).login(username, password);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.switchView(Kiosk.START_VIEW);
            }
        });
    }

    public void loginSuccess(){
        window.switchView(Kiosk.STOCK_VIEW);
    }

    public void loginFail(){
        JOptionPane.showMessageDialog(this, "The username or password is incorrect");
    }
}
