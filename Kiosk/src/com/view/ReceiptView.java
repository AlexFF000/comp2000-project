package com.view;

import com.Kiosk;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptView extends AbstractView{
    private JButton finishButton;
    private JScrollPane scrollPane;
    private JTextPane textPane;

    public ReceiptView(Kiosk window){
        setWindow(window);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Add receipt text area
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 0.9;
        constraints.weightx = 0.9;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(scrollPane, constraints);
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(finishButton, constraints);
        // Make receipt panel initially look disabled
        constraints.gridx = 1;
        constraints.gridy = 1;
        textPane.setText("Processing receipt, please wait...");
        textPane.setBackground(getBackground());
        finishButton.setEnabled(false);

        finishButton.addActionListener(e -> window.closeReceipt());
    }

    public void displayReceiptCard(String companyName, Date date, ReceiptItem[] items, float totalCost){
        StringBuilder htmlText = new StringBuilder();
        // Add company name
        htmlText.append(String.format("<center><h4>%s<h4></center><br>", companyName));
        // Add items
        addItems(htmlText, items, totalCost);
        // Add Payment method
        htmlText.append("<br><br><div style='text-align:left'>Payment Method:<br>Card</div>");
        String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        htmlText.append(String.format("<div>%s</div>", dateString));
        textPane.setText(htmlText.toString());
        textPane.setBackground(Color.WHITE);
        finishButton.setEnabled(true);
    }

    public void displayReceiptCash(String companyName, Date date, ReceiptItem[] items, float totalCost, float change){
        StringBuilder htmlText = new StringBuilder();
        // Add company name
        htmlText.append(String.format("<center><h4>%s<h4></center><br>", companyName));
        // Add items
        addItems(htmlText, items, totalCost);
        // Add Payment method
        htmlText.append("<br><br><div style='text-align:left'>Payment Method:<br>Cash</div>");
        htmlText.append(String.format("<br><div style='text-align:left'>Change Given:<br>£%.2f</div>", change));
        String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
        htmlText.append(String.format("<div>%s</div>", dateString));
        textPane.setText(htmlText.toString());
        textPane.setBackground(Color.WHITE);
        finishButton.setEnabled(true);
    }

    private void addItems(StringBuilder builder, ReceiptItem[] items, float totalCost){
        // Add items
        for (ReceiptItem item : items) {
            builder.append(String.format("<div style='text-align:left'>%s</div><div style='text-align:right'>£%.2f</div>", item.name, item.price));
        }
        // Add total
        builder.append(String.format("<br><div style='text-align:left'><strong>Total</strong></div><div style='text-align:right'><strong>£%.2f<strong></div>", totalCost));
    }
}
