package com.controller;

import com.model.JsonObject;
import com.model.StockItem;
import com.model.StockManager;

import java.util.ArrayList;

public class CheckoutController extends Controller{
    private ArrayList<StockItem> basket;

    @Override
    public void updateModel(int updateType, JsonObject newValue){
        if (updateType == UPDATE_ITEM_QUANTITY){
            // The only modification to the com.model that can be made by checkout is reducing item quantities
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

    }

    public void removeScannedItem(String barcode){

    }

    public void payCash(){

    }

    public void payCard(){

    }

    public void cancel(){

    }

    public void close(){

    }
}
