package com;

import com.controller.CheckoutController;
import com.controller.Controller;
import com.controller.InventoryController;
import com.controller.UsersController;
import com.model.OrderManager;
import com.model.StockManager;
import com.model.User;
import com.model.UserManager;
import com.view.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
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
        setPreferredSize(new Dimension(1000, 500));
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
                PaymentView paymentView = new PaymentView(this);
                paymentView.setController(controller);
                controller.setView(paymentView);
                changeMainPanel(paymentView);
                break;
            case LOGIN_VIEW:
                controller = new UsersController();
                LoginView loginView = new LoginView(this);
                loginView.setController(controller);
                controller.setView(loginView);
                changeMainPanel(loginView);
                break;
            case STOCK_VIEW:
                controller = new InventoryController();
                StockView stockView = new StockView(this);
                stockView.setController(controller);
                controller.setView(stockView);
                changeMainPanel(stockView);
                ((InventoryController) controller).displayStock();
                break;
            case USERS_VIEW:
                controller = new UsersController();
                UsersView usersView = new UsersView(this);
                usersView.setController(controller);
                controller.setView(usersView);
                changeMainPanel(usersView);
                ((UsersController) controller).displayUsers();
                break;
            case ORDERS_VIEW:
                changeMainPanel(new OrdersView(this));
                break;
        }
    }

    public void addReceiptView(){
        // Display receipt view in separate panel alongside mainPanel
        ReceiptView receiptView = new ReceiptView(this);
        receiptPanel = receiptView;
        receiptPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        add(receiptPanel);
        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        if (controller.getClass() == CheckoutController.class){
            controller.setView(receiptView);
            // Process receipt data in new thread
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    ((CheckoutController) controller).generateReceipt();
                }
            }.start();
        }
    }

    public void closeReceipt(){
        // Close receipt view and return to start view ready for next customer
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        remove(receiptPanel);
        // Remove the reference to the ReceiptView and CheckoutController to allow the garbage collector to dispose of them
        receiptPanel = null;
        controller = null;
        switchView(START_VIEW);
    }

    public static void main(String[] args){
        Kiosk window = new Kiosk();
        window.setVisible(true);
    }
}
