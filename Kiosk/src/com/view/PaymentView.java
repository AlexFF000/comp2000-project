package com.view;

import com.Kiosk;
import com.controller.CheckoutController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaymentView extends AbstractView{
    private JLabel titleText;
    private JLabel paymentMessageText;
    private JLabel pleaseWaitMessage;
    private JButton payCashButton;
    private JButton payCardButton;
    private JButton backButton;

    // Store event listeners so they can be removed later
    private ActionListener backButtonChoosePayment = e -> {
        // Back button clicked on choose payment form, so cancel the shop
        if (controller.getClass() == CheckoutController.class){
            ((CheckoutController) controller).cancel();
        }
        window.switchView(Kiosk.START_VIEW);
    };

    private ActionListener backButtonPaymentFailed = e -> {
        // Back button clicked on payment failed form, so go back to choose payment form
        displayPaymentOptions();
    };

    private ActionListener payCashButtonPress = e -> {
        // Cash option has been selected
        if (controller.getClass() == CheckoutController.class){
            ((CheckoutController) controller).payCash();
        }
    };

    private ActionListener payCardButtonPress = e -> {
        // Card option has been selected
        if (controller.getClass() == CheckoutController.class){
            ((CheckoutController) controller).payCard();
        }
    };

    public PaymentView(Kiosk window){
        setWindow(window);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        // Add title text
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleText, constraints);
        // Add paymentMessageText
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.gridy = 1;
        add(paymentMessageText, constraints);
        // Add payment options buttons
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.ipadx = payCardButton.getPreferredSize().width / 2;  // Make both buttons the same size
        add(payCashButton, constraints);
        constraints.ipadx = 0;
        constraints.gridx = 3;
        add(payCardButton, constraints);
        // Add "Please wait for receipt" message
        constraints.gridx = 2;
        constraints.gridy = 2;
        add(pleaseWaitMessage, constraints);
        // Add back button
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor =  GridBagConstraints.LAST_LINE_START;
        add(backButton, constraints);
        // Add empty space on the right with same width as back button
        constraints.gridx = 4;
        constraints.ipadx = backButton.getPreferredSize().width;
        add(Box.createGlue(), constraints);

        displayPaymentOptions();
    }

    private void clearAll(){
        // Hide all components
        titleText.setVisible(false);
        paymentMessageText.setVisible(false);
        payCashButton.setVisible(false);
        payCardButton.setVisible(false);
        pleaseWaitMessage.setVisible(false);
        backButton.setVisible(false);
        // Remove event listeners
        backButton.removeActionListener(backButtonChoosePayment);
        backButton.removeActionListener(backButtonPaymentFailed);
        payCashButton.removeActionListener(payCashButtonPress);
        payCardButton.removeActionListener(payCardButtonPress);
    }

    public void displayPaymentOptions(){
        // Prepare screen to allow user to choose payment option
        clearAll();
        titleText.setText("Select payment method");
        titleText.setVisible(true);
        payCashButton.setVisible(true);
        payCardButton.setVisible(true);
        backButton.setEnabled(true);
        backButton.setVisible(true);
        payCashButton.addActionListener(payCashButtonPress);
        payCardButton.addActionListener(payCardButtonPress);
        backButton.addActionListener(backButtonChoosePayment);
    }

    public void displayPaymentSuccess(String message){
        // Prepare screen for when payment was successful
        clearAll();
        titleText.setText("Payment Successful");
        titleText.setVisible(true);
        paymentMessageText.setText(message);
        paymentMessageText.setVisible(true);
        pleaseWaitMessage.setVisible(true);
        backButton.setEnabled(false);
        backButton.setVisible(true);
    }

    public void displayPaymentFail(String message){
        // Prepare screen for when payment failed
        clearAll();
        titleText.setText("Payment Failed");
        titleText.setVisible(true);
        paymentMessageText.setText(message);
        paymentMessageText.setVisible(true);
        backButton.setEnabled(true);
        backButton.setVisible(true);
        backButton.addActionListener(backButtonPaymentFailed);
    }

}
