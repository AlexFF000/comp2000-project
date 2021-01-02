package model;

import controller.Controller;

import java.util.ArrayList;

public class StockItem implements IObservable{
    public ArrayList<Controller> observers;
    private String barcode;
    private String name;
    private int quantityInStock;
    private int reorderLevel;
    private float salePrice;
    private float supplierPrice;

    public StockItem(String barcode, String name, int quantityInStock, int reorderLevel, float salePrice, float supplierPrice){
        // Unlike setters, constructor should not update the data file, so it can be used to create StockItem objects read from the file
        observers = new ArrayList<>();
        this.barcode = barcode;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.reorderLevel = reorderLevel;
        this.salePrice = salePrice;
        this.supplierPrice = supplierPrice;
    }

    public String getBarcode(){
        return barcode;
    }

    public void setBarcode(String newBarcode){
        barcode = newBarcode;
        updateAfterSet();
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
        updateAfterSet();
    }

    public int getQuantityInStock(){
        return quantityInStock;
    }

    public void setQuantityInStock(int newQuantity){
        quantityInStock = newQuantity;
        updateAfterSet();
    }

    public int getReorderLevel(){
        return reorderLevel;
    }

    public void setReorderLevel(int newLevel){
        reorderLevel = newLevel;
        updateAfterSet();
    }

    public float getSalePrice(){
        return salePrice;
    }

    public void setSalePrice(float newPrice){
        salePrice = newPrice;
        updateAfterSet();
    }

    public float getSupplierPrice(){
        return supplierPrice;
    }

    public void setSupplierPrice(float newPrice){
        supplierPrice = newPrice;
        updateAfterSet();
    }

    private void updateAfterSet(){
        // Save to file and notify observers
        StockManager.getInstance().saveToFile();
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
        for (Controller observer : observers){
            observer.updateViewStockItem(this);
        }
    }

    @Override
    public void notifyObserversOfDelete(){
        for (Controller observer : observers){
            observer.removeViewStockItem(this);
        }
    }
}
