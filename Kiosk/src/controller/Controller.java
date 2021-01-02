package controller;

import model.User;
import model.StockItem;

public abstract class Controller {
    public abstract void updateModel(int updateType, JsonObject newValue);
    public abstract void updateViewUser(User updatedUser);
    public abstract void updateViewStockItem(StockItem updatedItem);
    public abstract void removeViewUser(User user);
    public abstract void removeViewStockItem(StockItem item);
    public abstract void removeViewOrder(Order order);
    public void setView(AbstractView view);
}
