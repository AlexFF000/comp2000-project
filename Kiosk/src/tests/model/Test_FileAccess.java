package tests.model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.*;
import com.model.*;
import com.view.*;
import com.view.*;

public class Test_FileAccess {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test_usersReadItems() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject user1 = new JsonObject(new JsonObject.JsonBuilder(
                "JSmith"
        )
        .setPassword("12345678"));
        JsonObject user2 = new JsonObject(new JsonObject.JsonBuilder(
                "ABrown"
        )
        .setPassword("928765620"));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(user1);
        itemList.add(user2);

        // Write test objects to file as JSON
        JSONObject testJsonData = new JSONObject();
        JSONObject currentItem = new JSONObject();
        currentItem.put("password", user1.getPassword());
        currentItem.put("salt", user1.getSalt());
        testJsonData.put(user1.getKey(), currentItem);
        currentItem = new JSONObject();
        currentItem.put("password", user2.getPassword());
        currentItem.put("salt", user2.getSalt());
        testJsonData.put(user2.getKey(), currentItem);

        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());
        writer.close();

        // Read items from file and check they are correct
        UsersFileAccess fileAccess = new UsersFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("readItems", File.class);
        method.setAccessible(true);
        ArrayList<JsonObject> outputList = (ArrayList<JsonObject>) method.invoke(fileAccess, tempFile);
        assertTrue(checkJsonObjectListEqual(itemList, outputList));
    }

    @Test
    public void test_usersWriteItems() throws IOException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject user1 = new JsonObject(new JsonObject.JsonBuilder(
                "JSmith"
        ).setPassword("12345678"));
        JsonObject user2 = new JsonObject(new JsonObject.JsonBuilder(
                "ABrown"
        ).setPassword("928765620"));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(user1);
        itemList.add(user2);
        File tempFile = folder.newFile("testData.json");

        // Call method to write objects to file
        UsersFileAccess fileAccess = new UsersFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("writeItems", ArrayList.class, File.class);
        method.setAccessible(true);
        method.invoke(fileAccess, itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("JSmith");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("ABrown");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(new JsonObject.JsonBuilder((String)"JSmith")
        .setPassword((String)fileOutput1.get("password"))));
        outputList.add(new JsonObject(new JsonObject.JsonBuilder((String)"ABrown")
        .setPassword((String)fileOutput2.get("password"))));
        assertTrue(checkJsonObjectListEqual(itemList, outputList));
    }

    @Test
    public void test_stockReadItems() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "12345678910111213"
        )
        .setName("Test Item 1")
        .setQuantityInStock(25)
        .setReorderLevel(10)
        .setSalePrice(32.50f)
        .setSupplierPrice(12.45f));

        ArrayList<JsonObject> itemList = new ArrayList<>();
        itemList.add(item1);

        // Write test objects to file as JSON
        JSONObject testJsonData = new JSONObject();
        JSONObject currentItem = new JSONObject();
        currentItem.put("name", item1.getName());
        currentItem.put("quantityInStock", item1.getQuantityInStock());
        currentItem.put("reorderLevel", item1.getReorderLevel());
        currentItem.put("salePrice", item1.getSalePrice());
        currentItem.put("supplierPrice", item1.getSupplierPrice());
        testJsonData.put(item1.getKey(), currentItem);


        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());
        writer.close();

        // Read items from file and check they are correct
        StockFileAccess fileAccess = new StockFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("readItems", File.class);
        method.setAccessible(true);
        ArrayList<JsonObject> outputList = (ArrayList<JsonObject>) method.invoke(fileAccess, tempFile);
        assertTrue(checkJsonObjectListEqual(itemList, outputList));
    }

    @Test
    public void test_stockWriteItems() throws IOException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "12345678910111213"
        )
        .setName("Item1")
        .setQuantityInStock(25)
        .setReorderLevel(10)
        .setSalePrice(32.50f)
        .setSupplierPrice(12.45f));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "345689002842499304"
        )
        .setName("Item2")
        .setQuantityInStock(600)
        .setReorderLevel(300)
        .setSalePrice(6.32f)
        .setSupplierPrice(10.31f));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);
        File tempFile = folder.newFile("testData.json");

        // Call method to write objects to file
        StockFileAccess fileAccess = new StockFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("writeItems", ArrayList.class, File.class);
        method.setAccessible(true);
        method.invoke(fileAccess, itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("12345678910111213");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("345689002842499304");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(new JsonObject.JsonBuilder("12345678910111213")
        .setName((String)fileOutput1.get("name"))
        .setQuantityInStock(((Long)fileOutput1.get("quantityInStock")).intValue())
        .setReorderLevel(((Long)fileOutput1.get("reorderLevel")).intValue())
        .setSalePrice(((Double)fileOutput1.get("salePrice")).floatValue())
        .setSupplierPrice(((Double)fileOutput1.get("supplierPrice")).floatValue())));
        outputList.add(new JsonObject(new JsonObject.JsonBuilder("345689002842499304")
        .setName((String)fileOutput2.get("name"))
        .setQuantityInStock(((Long)fileOutput2.get("quantityInStock")).intValue())
        .setReorderLevel(((Long)fileOutput2.get("reorderLevel")).intValue())
        .setSalePrice(((Double)fileOutput2.get("salePrice")).floatValue())
        .setSupplierPrice(((Double)fileOutput2.get("supplierPrice")).floatValue())));
        assertTrue(checkJsonObjectListEqual(itemList, outputList));
    }

    @Test
    public void test_ordersReadItems() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "1"
        )
        .setItemBarcode("1234556778890")
        .setQuantityPurchased(50)
        .setOrderDate(new Date(1609516215000L))
        .setCost(322.50f));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "2"
        )
        .setItemBarcode("87380473583841")
        .setQuantityPurchased(500)
        .setOrderDate(new Date(1609459200000L))
        .setCost(906.10f));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);

        // Write test objects to file as JSON
        JSONObject item1Json = new JSONObject();
        item1Json.put("orderID", item1.getKey());
        item1Json.put("itemBarcode", item1.getItemBarcode());
        item1Json.put("quantityPurchased", item1.getQuantityPurchased());
        item1Json.put("orderDate", item1.getOrderDate().getTime());
        item1Json.put("cost", item1.getCost());
        JSONObject testJsonData = new JSONObject();
        testJsonData.put(item1.getKey(), item1Json);
        JSONObject item2Json = new JSONObject();
        item2Json.put("orderID", item2.getKey());
        item2Json.put("itemBarcode", item2.getItemBarcode());
        item2Json.put("quantityPurchased", item2.getQuantityPurchased());
        item2Json.put("orderDate", item2.getOrderDate().getTime());
        item2Json.put("cost", item2.getCost());
        testJsonData.put(item2.getKey(), item2Json);

        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());
        writer.close();

        // Read items from file and check they are correct
        OrdersFileAccess fileAccess = new OrdersFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("readItems", File.class);
        method.setAccessible(true);
        ArrayList<JsonObject> outputList = (ArrayList<JsonObject>) method.invoke(fileAccess, tempFile);
        assertTrue(checkJsonObjectListEqual(itemList, outputList));

    }

    @Test
    public void test_ordersWriteItems() throws IOException, ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "1"
        )
        .setItemBarcode("1234556778890")
        .setQuantityPurchased(50)
        .setOrderDate(new Date(1609516215000L))
        .setCost(322.50f));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "2"
        )
        .setItemBarcode("87380473583841")
        .setQuantityPurchased(500)
        .setOrderDate(new Date(1609459200000L))
        .setCost(906.10f));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);
        File tempFile = folder.newFile("testData.json");

        // Call method to write objects to file
        OrdersFileAccess fileAccess = new OrdersFileAccess();
        Method method = fileAccess.getClass().getDeclaredMethod("writeItems", ArrayList.class, File.class);
        method.setAccessible(true);
        method.invoke(fileAccess, itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("1");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("2");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(new JsonObject.JsonBuilder((String)"1")
        .setItemBarcode((String)fileOutput1.get("itemBarcode"))
        .setQuantityPurchased(((Long)fileOutput1.get("quantityPurchased")).intValue())
        .setOrderDate(new Date((long)fileOutput1.get("orderDate")))
        .setCost(((Double)fileOutput1.get("cost")).floatValue())));
        outputList.add(new JsonObject(new JsonObject.JsonBuilder("2")
        .setItemBarcode((String)fileOutput2.get("itemBarcode"))
        .setQuantityPurchased(((Long)fileOutput2.get("quantityPurchased")).intValue())
        .setOrderDate(new Date(((long)fileOutput2.get("orderDate"))))
        .setCost(((Double)fileOutput2.get("cost")).floatValue())));
        assertTrue(checkJsonObjectListEqual(itemList, outputList));
    }

    public boolean checkJsonObjectListEqual(ArrayList<JsonObject> list1, ArrayList<JsonObject> list2){
        // Need to check each field by value, not just reference, so assertEquals cannot be used

        if (list1.size() != list2.size()){
            return false;
        }
        for (int i = 0; i < list1.size(); i++){
            JsonObject object1 = list1.get(i);
            JsonObject object2 = list2.get(i);
            // Check null values
            if (!(object1.getKey() == null) == (object2.getKey() == null)) return false;
            if (!(object1.getPassword() == null) == (object2.getPassword() == null)) return false;
            if (!(object1.getName() == null) == (object2.getName() == null)) return false;
            if (!(object1.getItemBarcode() == null) == (object2.getItemBarcode() == null)) return false;
            if (!((object1.getOrderDate() == null) == (object2.getOrderDate() == null))) return false;
            if (!(object1.getNewKey() == null) == (object2.getNewKey() == null)) return false;
            if (!(object1.getSalt() == null) == (object2.getSalt() == null)) return false;

            if (object1.getKey() != null) if (!object1.getKey().equals(object2.getKey())) return false;
            if (object1.getPassword() != null) if (!object1.getPassword().equals(object2.getPassword())) return false;
            if (object1.getName() != null) if (!object1.getName().equals(object2.getName())) return false;
            if (!(object1.getQuantityInStock() == object2.getQuantityInStock())) return false;
            if (!(object1.getReorderLevel() == object2.getReorderLevel())) return false;
            if (!(object1.getSalePrice() == object2.getSalePrice())) return false;
            if (!(object1.getSupplierPrice() == object2.getSupplierPrice())) return false;
            if (object1.getItemBarcode() != null) if (!(object1.getItemBarcode().equals(object2.getItemBarcode()))) return false;
            if (!(object1.getQuantityPurchased() == object2.getQuantityPurchased())) return false;
            if (object1.getOrderDate() != null) if (!(object1.getOrderDate().equals(object2.getOrderDate()))) return false;
            if (!(object1.getCost() == object2.getCost())) return false;
            if (object1.getNewKey() != null) if (!object1.getNewKey().equals(object2.getNewKey())) return false;
            if (object1.getSalt() != null) if (!object1.getSalt().equals(object2.getSalt())) return false;
        }
        return true;
    }

}
