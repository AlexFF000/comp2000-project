package model;

import controller.Controller;

import java.util.ArrayList;

import static model.JsonDirector.BuildJsonOrder;
import static model.JsonDirector.JsonToOrder;

public class OrderManager implements IModelManager{
    private static OrderManager instance;
    private final OrdersFileAccess file;
    public ArrayList<Order> orders;

    public static OrderManager getInstance(){
        if (instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    private OrderManager(){
        orders = new ArrayList<>();
        // Load orders from file
        file = new OrdersFileAccess();
        ArrayList<JsonObject> ordersJson = file.loadItems();
        for (JsonObject order : ordersJson){
            orders.add(JsonToOrder(order));
        }
    }

    public Order getOrder(String orderID){
        for (Order order : orders){
            if (order.getOrderID().equals(orderID)) return order;
        }
        return null;
    }

    public void addOrder(Order order){
        orders.add(order);
        saveToFile();
    }

    public void deleteOrder(Order order){
        order.notifyObserversOfDelete();
        orders.remove(order);
        saveToFile();
    }

    @Override
    public void register(Controller observer){
        for (Order order : orders){
            order.register(observer);
        }
    }

    @Override
    public void remove(Controller observer){
        for (Order order : orders){
            order.remove(observer);
        }
    }

    @Override
    public void saveToFile(){
        ArrayList<JsonObject> ordersJson = new ArrayList<>();
        for (Order order : orders){
            ordersJson.add(BuildJsonOrder(order));
        }
        file.saveItems(ordersJson);
    }
}
