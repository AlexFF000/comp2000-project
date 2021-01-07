package com.view;

import com.Kiosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartView extends AbstractView{
    private JButton staffLoginBtn;
    private JButton startBtn;
    private JLabel welcomeText;
    private JLabel startText;

    public StartView(Kiosk window){
        setWindow(window);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        // Place login button
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(staffLoginBtn, constraints);
        // Add Welcome text
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.125;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        add(welcomeText, constraints);
        // Add start shopping text
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        add(startText, constraints);
        // Add start button
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 0.25;
        constraints.weighty = 0.25;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(startBtn, constraints);
        // Add empty space at bottom
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.weightx = 0.5;
        constraints.weighty = 0.25;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        add(Box.createGlue(), constraints);
        // Add empty space on right side
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx = staffLoginBtn.getPreferredSize().width;  // Blank space on right should be same size as login button
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        add(Box.createGlue(), constraints);

        // Setup event listeners

        staffLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLogin();
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCheckout();
            }
        });
    }

    private void startCheckout(){
        // Switch the checkout view
        window.switchView(Kiosk.CHECKOUT_VIEW);
    }

    private void startLogin(){
        // Switch to the login view
        window.switchView(Kiosk.LOGIN_VIEW);
    }
}
