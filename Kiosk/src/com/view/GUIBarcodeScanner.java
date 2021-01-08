package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIBarcodeScanner extends JFrame implements IBarcodeScanner{
    private JTextField barcodeText;
    private JPanel panel1;
    private JButton inputBarcodeButton;
    private JLabel enterBarcodeLabel;

    private ArrayList<IBarcodeScannerObserver> observers;

    public GUIBarcodeScanner(){
        observers = new ArrayList<>();
        setTitle("Barcode Scanner");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(250, 150));
        pack();

        inputBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = barcodeText.getText();
                barcodeText.setText("");
                scanBarcode(barcode);
            }
        });
    }

    public void register(IBarcodeScannerObserver observer){
        observers.add(observer);
    }

    public void remove(IBarcodeScannerObserver observer){
        observers.remove(observer);
    }

    public void scanBarcode(String barcode){
        for (IBarcodeScannerObserver observer: observers){
            observer.useBarcode(barcode);
        }
    }
}
