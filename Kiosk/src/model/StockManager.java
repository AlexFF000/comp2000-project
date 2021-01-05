package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static model.JsonDirector.BuildJsonStockItem;
import static model.JsonDirector.JsonToStockItem;

public class StockManager implements IModelManager{
    private static StockManager instance;
    private final StockFileAccess file;
    // Use a dictionary instead of a list so items can quickly be fetched using their barcodes in O(1)
    public Map<String, StockItem> stock;

    public static StockManager getInstance(){
        if (instance == null){
            instance = new StockManager();
        }
        return instance;
    }

    private StockManager(){
        // Load stock from file
        file = new StockFileAccess();
        stock = new HashMap<>();
        ArrayList<JsonObject> stockJson = file.loadItems();
        for (JsonObject item : stockJson){
            stock.put(item.getKey(), JsonToStockItem(item));
        }
    }

    public StockItem getStockItem(String barcode){
        return stock.getOrDefault(barcode, null);
    }

    public void addStockItem(StockItem item){
        stock.put(item.getBarcode(), item);
        saveToFile();
    }

    public void deleteStockItem(StockItem item){
        item.notifyObserversOfDelete();
        stock.remove(item.getBarcode());
        saveToFile();
    }

    @Override
    public void register(Controller observer){
        for (StockItem item : stock.values()){
            item.register(observer);
        }
    }

    @Override
    public void remove(Controller observer){
        for (StockItem item : stock.values()){
            item.remove(observer);
        }
    }

    @Override
    public void saveToFile(){
        ArrayList<JsonObject> stockJson = new ArrayList<>();
        for (StockItem item : stock.values()){
            stockJson.add(BuildJsonStockItem(item));
        }
        file.saveItems(stockJson);
    }
}
