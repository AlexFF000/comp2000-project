package controller;

import model.User;
import model.StockItem;
import model.Order;
import model.JsonObject;
import view.AbstractView;

public abstract class Controller {
    // Constant parameters for updateModel's updateType argument
    public static final int CREATE_USER = 0;
    public static final int CREATE_STOCKITEM = 1;
    public static final int CREATE_ORDER = 2;
    public static final int DELETE_USER = 3;
    public static final int DELETE_STOCKITEM = 4;
    public static final int DELETE_ORDER = 5;
    public static final int ORDER_DELIVERED = 6;
    public static final int UPDATE_USERNAME = 7;
    public static final int UPDATE_ITEM_BARCODE = 8;
    public static final int UPDATE_PASSWORD = 9;
    public static final int UPDATE_ITEM_NAME = 10;
    public static final int UPDATE_ITEM_QUANTITY = 11;
    public static final int UPDATE_REORDER_LEVEL = 12;
    public static final int UPDATE_SALE_PRICE = 13;
    public static final int UPDATE_SUPPLIER_PRICE = 14;

    public AbstractView view;

    public abstract void updateModel(int updateType, JsonObject newValue);

    public void updateViewUser(User updatedUser) {}
    public void updateViewStockItem(StockItem updatedItem) {}
    public void removeViewUser(User user) {}
    public void removeViewStockItem(StockItem item) {}
    public void removeViewOrder(Order order) {}

    public void setView(AbstractView view){
        this.view = view;
    }
}
