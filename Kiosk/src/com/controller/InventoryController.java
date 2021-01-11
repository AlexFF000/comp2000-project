package com.controller;

import com.model.JsonObject;
import com.model.Order;
import com.model.OrderManager;
import com.model.StockItem;
import com.model.StockManager;
import com.view.CheckoutView;
import com.view.StockView;

import static com.model.JsonDirector.JsonToStockItem;
import static com.model.JsonDirector.JsonToOrder;

public class InventoryController extends Controller{

    @Override
    public void updateModel(int updateType, JsonObject newValue){
        StockManager stockManager = StockManager.getInstance();
        OrderManager orderManager = OrderManager.getInstance();
        Order order;
        StockItem stockItem;
        switch (updateType){
            case CREATE_STOCKITEM:
                stockManager.addStockItem(JsonToStockItem(newValue));
                if (view.getClass() == StockView.class) {
                    StockItem newItem = stockManager.getStockItem(newValue.getKey());
                    if (newItem != null) {
                        // Register as an observer, and add new item to view
                        newItem.register(this);
                        ((StockView) view).addToDisplay(
                                newItem.getBarcode(),
                                newItem.getName(),
                                newItem.getQuantityInStock(),
                                newItem.getReorderLevel(),
                                newItem.getSalePrice(),
                                newItem.getSupplierPrice());
                    }
                }
                break;
            case DELETE_STOCKITEM:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockManager.deleteStockItem(stockItem);
                break;
            case UPDATE_ITEM_BARCODE:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setBarcode(newValue.getNewKey());
                break;
            case UPDATE_ITEM_NAME:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setName(newValue.getName());
                break;
            case UPDATE_ITEM_QUANTITY:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setQuantityInStock(newValue.getQuantityInStock());
                break;
            case UPDATE_REORDER_LEVEL:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setReorderLevel(newValue.getReorderLevel());
                break;
            case UPDATE_SALE_PRICE:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setSalePrice(newValue.getSalePrice());
                break;
            case UPDATE_SUPPLIER_PRICE:
                stockItem = stockManager.getStockItem(newValue.getKey());
                if (stockItem != null) stockItem.setSupplierPrice(newValue.getSupplierPrice());
                break;
            case CREATE_ORDER:
                orderManager.addOrder(JsonToOrder(newValue));
                break;
            case DELETE_ORDER:
                order = orderManager.getOrder(newValue.getKey());
                if (order != null) orderManager.deleteOrder(order);
                break;
            case ORDER_DELIVERED:
                // Update quantity in stock and delete order
                stockItem = stockManager.getStockItem(newValue.getItemBarcode());
                order = orderManager.getOrder(newValue.getKey());
                if (order != null && stockItem != null){
                    stockItem.setQuantityInStock(stockItem.getQuantityInStock() + order.getQuantityPurchased());
                    orderManager.deleteOrder(order);
                }
                break;
        }
    }

    @Override
    public void updateViewStockItem(StockItem updatedItem){
        // Update display to reflect changes
        if (view.getClass() == StockView.class){
            StockView stockView = (StockView) view;
            String barcode = updatedItem.getBarcode();
            stockView.editDisplayedItem(barcode, "name", updatedItem.getName());
            stockView.editDisplayedItem(barcode, "quantityInStock", updatedItem.getQuantityInStock());
            stockView.editDisplayedItem(barcode, "reorderLevel", updatedItem.getReorderLevel());
            stockView.editDisplayedItem(barcode, "salePrice", updatedItem.getSalePrice());
            stockView.editDisplayedItem(barcode, "supplierPrice", updatedItem.getSupplierPrice());
        }
    }

    @Override
    public void removeViewStockItem(StockItem item) {
        if (view.getClass() == StockView.class){
            ((StockView) view).removeDisplayedItem(item.getBarcode());
        }
    }

    public void displayStock(){
        StockManager manager = StockManager.getInstance();
        for (StockItem item : manager.stock.values()){
            // Register as an observer and display the stock
            item.register(this);
            // Add to display
            if (view.getClass() == StockView.class){
                ((StockView) view).addToDisplay(
                        item.getBarcode(),
                        item.getName(),
                        item.getQuantityInStock(),
                        item.getReorderLevel(),
                        item.getSalePrice(),
                        item.getSupplierPrice());
            }
        }
    }
}
