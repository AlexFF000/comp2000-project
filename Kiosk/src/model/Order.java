package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.Date;

public class Order implements IObservable{
    public ArrayList<Controller> observers;
    private String orderID;
    private String itemBarcode;
    private int quantityPurchased;
    private Date orderDate;
    private float cost;

    public Order(String orderID, String itemBarcode, int quantityPurchased, Date orderDate, float cost){
        // Unlike setters, constructor should not update the data file, so it can be used to create Order objects read from the file
        observers = new ArrayList<>();
        this.orderID = orderID;
        this.itemBarcode = itemBarcode;
        this.quantityPurchased = quantityPurchased;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public String getOrderID(){
        return orderID;
    }

    public void setOrderID(String newID){
        orderID = newID;
        updateAfterSet();
    }

    public String getBarcode(){
        return itemBarcode;
    }

    public void setBarcode(String newBarcode){
        itemBarcode = newBarcode;
        updateAfterSet();
    }

    public int getQuantityPurchased(){
        return quantityPurchased;
    }

    public void setQuantityPurchased(int newQuantity) {
        quantityPurchased = newQuantity;
        updateAfterSet();
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(Date newDate){
        orderDate = newDate;
        updateAfterSet();
    }

    public float getCost(){
        return cost;
    }

    public void setCost(float newCost){
        cost = newCost;
        updateAfterSet();
    }

    private void updateAfterSet(){
        // Save to file and notify observers
        OrderManager.getInstance().saveToFile();
        notifyObserversOfUpdate();
    }

    @Override
    public void register(Controller observer){
        observers.add(observer);
    }

    @Override
    public void remove(Controller observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObserversOfUpdate(){
        // Orders cannot be updated, only added and removed
    }

    @Override
    public void notifyObserversOfDelete(){
        for (Controller observer : observers){
            observer.removeViewOrder(this);
        }
    }
}
