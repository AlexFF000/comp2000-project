package com;

import com.view.StartView;

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

    public Kiosk(){
        mainPanel = new StartView();
        initialise();
    }

    public void initialise(){
        GridLayout layout = new GridLayout();
        setLayout(layout);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        pack();
        mainPanel.setVisible(true);
    }

    public void switchView(int view){

    }

    public void addReceiptView(){

    }

    public static void main(String[] args){
        Kiosk window = new Kiosk();
        window.setVisible(true);
    }
}
