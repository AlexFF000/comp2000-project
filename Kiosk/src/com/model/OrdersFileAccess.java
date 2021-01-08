package com.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OrdersFileAccess extends FileAccess{

    @Override
    protected File getFile(){
        try {
            File file = new File("resources\\orders.json");
            // Create the file if it does not already exist
            file.createNewFile();
            return file;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<JsonObject> readItems(File file){
        ArrayList<JsonObject> orders = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            String fileContent = "";
            if (reader.hasNextLine()) {
                while (reader.hasNextLine()) {
                    // Read file to string
                    fileContent += reader.nextLine();
                }
                // Parse Json to a Java object
                JSONParser parser = new JSONParser();
                // The JSONObject from the simple.Json library is different to model.JsonObject
                JSONObject data = (JSONObject) parser.parse(fileContent);
                // Iterate over each item read from the file and create a model.JsonObject from it
                for (String orderID : (Iterable<String>) data.keySet()) {
                    // The orderID is used as the key in the Json dictionary
                    JSONObject currentItem = (JSONObject) data.get(orderID);
                    JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(orderID)
                            .setItemBarcode((String) currentItem.get("itemBarcode"))
                            .setQuantityPurchased(((Long) currentItem.get("quantityPurchased")).intValue())
                            .setOrderDate(new Date((Long)currentItem.get("orderDate")))
                            .setCost(((Double) currentItem.get("cost")).floatValue());
                    orders.add(new JsonObject(builder));
                }
            }
            reader.close();
        }
        catch (FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    protected void writeItems(ArrayList<JsonObject> items, File file){
        try{
            // Convert model.JsonObjects a json.simple JSONObject
            JSONObject data = new JSONObject();
            for (JsonObject item : items){
                JSONObject currentItem = new JSONObject();
                currentItem.put("itemBarcode", item.getItemBarcode());
                currentItem.put("quantityPurchased", item.getQuantityPurchased());
                currentItem.put("orderDate", item.getOrderDate().getTime());
                currentItem.put("cost", item.getCost());
                // Use barcode as key in Json file
                data.put(item.getKey(), currentItem);
            }
            // Write to file, overwriting any existing content
            FileWriter writer = new FileWriter(file);
            writer.write(data.toJSONString());
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
