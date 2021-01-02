package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mockito.internal.matchers.Or;

public class UsersFileAccess extends FileAccess{

    @Override
    protected File getFile(){
        try {
            File file = new File("resources\\users.json");
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
        ArrayList<JsonObject> users = new ArrayList<>();
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
                for (String username : (Iterable<String>) data.keySet()) {
                    // The username is used as the key in the Json dictionary
                    JSONObject currentUser = (JSONObject) data.get(username);
                    JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(username)
                            .setPassword((String) currentUser.get("password"));
                    users.add(new JsonObject(builder));
                }
            }
            reader.close();
        }
        catch (FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    protected void writeItems(ArrayList<JsonObject> items, File file){
        try{
            // Convert model.JsonObjects a json.simple JSONObject
            JSONObject data = new JSONObject();
            for (JsonObject item : items){
                JSONObject currentItem = new JSONObject();
                currentItem.put("password", item.getPassword());
                // Use username as key in Json file
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
