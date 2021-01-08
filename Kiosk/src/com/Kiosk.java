package com;

import com.controller.CheckoutController;
import com.controller.Controller;
import com.model.OrderManager;
import com.model.StockManager;
import com.model.UserManager;
import com.view.*;

import javax.swing.*;
import java.awt.*;

public class Kiosk extends JFrame {
    public static final int START_VIEW = 0;
    public static final int CHECKOUT_VIEW = 1;
    public static final int PAYMENT_VIEW = 2;
    public static final int LOGIN_VIEW = 3;
    public static final int STOCK_VIEW = 4;
    public static final int USERS_VIEW = 5;
    public static final int ORDERS_VIEW = 6;
    private JPanel mainPanel;
    private JPanel receiptPanel;

    private Controller controller;
    private GUIBarcodeScanner barcodeScanner;

    public Kiosk(){
        mainPanel = new StartView(this);
        initialise();
    }

    public void initialise(){
        GridLayout layout = new GridLayout();
        setLayout(layout);
        add(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();
        mainPanel.setVisible(true);
    }

    private void changeMainPanel(JPanel panel){
        // Remove mainPanel if it already exists
        remove(mainPanel);
        mainPanel = panel;
        add(mainPanel);
        // Tell swing to repaint page
        validate();
        repaint();
        mainPanel.setVisible(true);
    }

    public void switchView(int view){
        if (barcodeScanner != null){
            // Close the barcode scanner when switching from CheckoutView
            barcodeScanner.dispose();
            barcodeScanner = null;
        }
        switch (view){
            case START_VIEW:
                changeMainPanel(new StartView(this));
                break;
            case CHECKOUT_VIEW:
                controller = new CheckoutController();
                CheckoutView checkoutView = new CheckoutView(this);
                checkoutView.setController(controller);
                // Setup barcode scanner
                barcodeScanner = new GUIBarcodeScanner();
                barcodeScanner.setVisible(true);
                barcodeScanner.register(checkoutView);
                controller.setView(checkoutView);
                changeMainPanel(checkoutView);
                break;
            case PAYMENT_VIEW:
                changeMainPanel(new PaymentView(this));
                break;
            case LOGIN_VIEW:
                changeMainPanel(new LoginView(this));
                break;
            case STOCK_VIEW:
                changeMainPanel(new StockView(this));
                break;
            case USERS_VIEW:
                changeMainPanel(new UsersView(this));
                break;
            case ORDERS_VIEW:
                changeMainPanel(new OrdersView(this));
                break;
        }
    }

    public void addReceiptView(){

    }

    public static void main(String[] args){
        Kiosk window = new Kiosk();
        window.setVisible(true);
    }
}
