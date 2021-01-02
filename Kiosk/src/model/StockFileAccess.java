package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockFileAccess extends FileAccess{
    @Override
    protected File getFile(){
        try {
            File file = new File("resources\\stock.json");
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
        ArrayList<JsonObject> items = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            String fileContent = "";
            if (reader.hasNext()) {
                // Read file to string
                fileContent = reader.next();
                // Parse Json to a Java object
                JSONParser parser = new JSONParser();
                // The JSONObject from the simple.Json library is different to model.JsonObject
                JSONObject data = (JSONObject) parser.parse(fileContent);
                // Iterate over each item read from the file and create a model.JsonObject from it
                for (String barcode : (Iterable<String>) data.keySet()) {
                    // The barcode is used as the key in the Json dictionary
                    JSONObject currentItem = (JSONObject) data.get(barcode);
                    JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(barcode)
                            .setName((String) currentItem.get("name"))
                            .setQuantityInStock(((Long) currentItem.get("quantityInStock")).intValue())
                            .setReorderLevel(((Long) currentItem.get("reorderLevel")).intValue())
                            .setSalePrice(((Double) currentItem.get("salePrice")).floatValue())
                            .setSupplierPrice(((Double) currentItem.get("supplierPrice")).floatValue());
                    items.add(new JsonObject(builder));
                }
            }
            reader.close();
        }
        catch (FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
        return items;
    }

    @Override
    protected void writeItems(ArrayList<JsonObject> items, File file){
        try{
            // Convert model.JsonObjects a json.simple JSONObject
            JSONObject data = new JSONObject();
            for (JsonObject item : items){
                JSONObject currentItem = new JSONObject();
                currentItem.put("name", item.getName());
                currentItem.put("quantityInStock", item.getQuantityInStock());
                currentItem.put("reorderLevel", item.getReorderLevel());
                currentItem.put("salePrice", item.getSalePrice());
                currentItem.put("supplierPrice", item.getSupplierPrice());
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
