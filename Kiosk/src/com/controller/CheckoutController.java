package com.controller;

import com.model.JsonObject;
import com.model.StockItem;
import com.model.StockManager;
import com.view.CheckoutView;
import com.view.PaymentView;
import com.view.ReceiptItem;
import com.view.ReceiptView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CheckoutController extends Controller{
    public static final String companyName = "PlymMarket";
    private ArrayList<StockItem> basket;
    private float total;
    private PaymentStatus paymentResult;
    private int paymentType;

    public CheckoutController(){
        total = 0f;
        basket = new ArrayList<>();
    }

    @Override
    public void updateModel(int updateType, JsonObject newValue){
        if (updateType == UPDATE_ITEM_QUANTITY){
            // The only modification to the model that can be made by checkout is reducing item quantities
            String barcode = newValue.getKey();
            int newQuantity = newValue.getQuantityInStock();
            StockItem item = StockManager.getInstance().getStockItem(barcode);
            if (item != null) item.setQuantityInStock(newQuantity);
        }
    }

    public void scanItem(String barcode){
        // Try to get item with given barcode
        StockItem item = StockManager.getInstance().getStockItem(barcode);
        if (item != null){
            // Register as an observer with the item
            item.register(this);
            // Add item to basket
            basket.add(item);
            total += item.getSalePrice();
            // Decrement the item's quantity in stock
            item.setQuantityInStock(item.getQuantityInStock() - 1);
            // Add item to display
            if (view.getClass() == CheckoutView.class){
                CheckoutView checkoutView = (CheckoutView) view;
                checkoutView.addItemToDisplay(barcode, item.getName(), item.getSalePrice());
                checkoutView.setTotal(total);
            }
        }
    }

    public void removeScannedItem(String barcode){
        // Remove all instances of the item from the basket
        // Use Iterator instead of standard foreach loop to avoid ConcurrentModificationException when removing an item
        for (Iterator<StockItem> iterator = basket.iterator(); iterator.hasNext();){
            StockItem item = iterator.next();
            if (item.getBarcode().equals(barcode)) {
                // Deregister as an observer
                item.remove(this);
                // Item is no longer reserved, so increase quantity by 1
                item.setQuantityInStock(item.getQuantityInStock() + 1);
                // Remove from total
                total -= item.getSalePrice();
                iterator.remove();
            }
        }
        // Remove item from view
        if (view.getClass() == CheckoutView.class){
            CheckoutView checkoutView = (CheckoutView) view;
            checkoutView.removeItemFromDisplay(barcode);
            checkoutView.setTotal(total);
        }
    }

    public void payCash(){
        paymentType = 0;
        IPaymentSystem paymentSystem = new CashInput();
        paymentResult = paymentSystem.HandlePayment(0.00f);
        if (paymentResult.success){
            if (view.getClass() == PaymentView.class){
                ((PaymentView) view).displayPaymentSuccess(paymentResult.message);
            }
        }
        else{
            if (view.getClass() == PaymentView.class){
                ((PaymentView) view).displayPaymentFail(paymentResult.message);
            }
        }
    }

    public void payCard(){
        paymentType = 1;
        IPaymentSystem paymentSystem = new CardReader();
        paymentResult = paymentSystem.HandlePayment(0f);
        if (paymentResult.success){
            if (view.getClass() == PaymentView.class){
                ((PaymentView) view).displayPaymentSuccess(paymentResult.message);
            }
        }
        else{
            if (view.getClass() == PaymentView.class){
                ((PaymentView) view).displayPaymentFail(paymentResult.message);
            }
        }
    }

    public void generateReceipt(){
        // Process items in basket
        ReceiptItem[] receiptItems = new ReceiptItem[basket.size()];
        for (int i = 0; i < basket.size(); i++){
            StockItem item = basket.get(i);
            receiptItems[i] = new ReceiptItem(item.getName(), item.getSalePrice());
            // Also deregister from the item at the same time, as it is no longer needed
            item.remove(this);
        }
        if (paymentType == 0) {  // Type 0: cash
            float change;
            try {
                // Get change due from payment message string
                change = Float.parseFloat(paymentResult.message.split("£")[1]);
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
                // Default to 0.00 in case of error
                change = 0f;
            }
            // Update receipt in UI thread
            float finalChange = change;  // Compiler won't allow the variable to be used in lambda if it can't be sure it won't change, so must create new variable to convince it
            SwingUtilities.invokeLater(() ->{
                if (view.getClass() == ReceiptView.class){
                    ((ReceiptView) view).displayReceiptCash(companyName, new Date(), receiptItems, total, finalChange);
                }
            });
        }
        else{  // Card
            SwingUtilities.invokeLater(() -> {
                if (view.getClass() == ReceiptView.class){
                    ((ReceiptView) view).displayReceiptCard(companyName, new Date(), receiptItems, total);
                }
            });
        }

    }

    public void cancel(){
        // Deregister and remove all items from basket
        for (Iterator<StockItem> iterator = basket.iterator(); iterator.hasNext();){
            StockItem item = iterator.next();
            // Deregister as an observer
            item.remove(this);
            // Item is no longer reserved, so increase quantity by 1
            item.setQuantityInStock(item.getQuantityInStock() + 1);
            iterator.remove();
        }
    }
}
