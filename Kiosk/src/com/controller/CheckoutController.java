package com.controller;

import com.model.JsonObject;
import com.model.StockItem;
import com.model.StockManager;
import com.view.CheckoutView;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckoutController extends Controller{
    private ArrayList<StockItem> basket;

    public CheckoutController(){
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

    @Override
    public void updateViewStockItem(StockItem updatedItem){

    }

    @Override
    public void removeViewStockItem(StockItem item){

    }

    public void scanItem(String barcode){
        // Try to get item with given barcode
        StockItem item = StockManager.getInstance().getStockItem(barcode);
        if (item != null){
            // Register as an observer with the item
            item.register(this);
            // Add item to basket
            basket.add(item);
            // Decrement the item's quantity in stock
            item.setQuantityInStock(item.getQuantityInStock() - 1);
            // Add item to display
            if (view.getClass() == CheckoutView.class){
                ((CheckoutView) view).addItemToDisplay(barcode, item.getName(), item.getSalePrice());
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
                iterator.remove();
            }
        }
        // Remove item from view
        if (view.getClass() == CheckoutView.class){
            ((CheckoutView) view).removeItemFromDisplay(barcode);
        }
    }

    public void payCash(){

    }

    public void payCard(){

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

    public void close(){

    }
}
