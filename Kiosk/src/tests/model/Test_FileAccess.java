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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.simple.*;

public class Test_FileAccess {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test_usersReadItems() throws IOException {
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
        testJsonData.put(user1.key, user1);
        testJsonData.put(user2.key, user2);

        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());

        // Read items from file and check they are correct
        UsersFileAccess fileAccess = new UsersFileAccess();
        ArrayList<JsonObject> outputList = fileAccess.readItems(tempFile);
        assertEquals(itemList, outputList);
    }

    @Test
    public void test_usersWriteItems() throws IOException, ParseException {
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
        fileAccess.writeItems(itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("JSmith");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("ABrown");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput1.get("username"))
        .setPassword((String)fileOutput1.get("password"))));
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput2.get("username"))
        .setPassword((String)fileOutput2.get("password"))));
        assertEquals(itemList, outputList);
    }

    @Test
    public void test_stockReadItems() throws IOException{
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "12345678910111213"
        )
        .setName("Test Item 1")
        .setQuantityInStock(25)
        .setReorderLevel(10)
        .setSalePrice(32.50)
        .setSupplierPrice(12.45));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "345689002842499304"
        )
        .setName("Test Item 2")
        .setQuantityInStock(600)
        .setReorderLevel(300)
        .setSalePrice(6.32)
        .setSupplierPrice(10.31));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);

        // Write test objects to file as JSON
        JSONObject testJsonData = new JSONObject();
        testJsonData.put(item1.key, item1);
        testJsonData.put(item2.key, item2);

        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());

        // Read items from file and check they are correct
        StockFileAccess fileAccess = new StockFileAccess();
        ArrayList<JsonObject> outputList = fileAccess.readItems(tempFile);
        assertEquals(itemList, outputList);
    }

    @Test
    public void test_stockWriteItems() throws IOException, ParseException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "12345678910111213"
        )
        .setName("Test Item 1")
        .setQuantityInStock(25)
        .setReorderLevel(10)
        .setSalePrice(32.50)
        .setSupplierPrice(12.45));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "345689002842499304"
        )
        .setName("Test Item 2")
        .setQuantityInStock(600)
        .setReorderLevel(300)
        .setSalePrice(6.32)
        .setSupplierPrice(10.31));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);
        File tempFile = folder.newFile("testData.json");

        // Call method to write objects to file
        StockFileAccess fileAccess = new StockFileAccess();
        fileAccess.writeItems(itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("12345678910111213");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("345689002842499304");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput1.get("barcode"))
        .setName((String)fileOutput1.get("name"))
        .setQuantityInStock((int)fileOutput1.get("quantityInStock"))
        .setReorderLevel((int)fileOutput1.get("reorderLevel"))
        .setSalePrice((float)fileOutput1.get("salePrice"))
        .setSupplierPrice((float)fileOutput1.get("supplierPrice"))));
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput2.get("barcode"))
        .setName((String)fileOutput2.get("name"))
        .setQuantityInStock((int)fileOutput2.get("quantityInStock"))
        .setReorderLevel((int)fileOutput2.get("reorderLevel"))
        .setSalePrice((float)fileOutput2.get("salePrice"))
        .setSupplierPrice((float)fileOutput2.get("supplierPrice"))));
        assertEquals(itemList, outputList);
    }

    @Test
    public void test_ordersReadItems() throws IOException{
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "1"
        )
        .setItemBarcode("1234556778890")
        .setQuantityPurchased(50)
        .setOrderDate(new Date(1609516215000L))
        .setCost(322.50));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "2"
        )
        .setItemBarcode("87380473583841")
        .setQuantityPurchased(500)
        .setOrderDate(new Date(1609459200000L))
        .setCost(906.10));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);

        // Write test objects to file as JSON
        JSONObject testJsonData = new JSONObject();
        testJsonData.put(item1.key, item1);
        testJsonData.put(item2.key, item2);

        File tempFile = folder.newFile("testData.json");
        FileWriter writer = new FileWriter(tempFile);
        writer.write(testJsonData.toJSONString());

        // Read items from file and check they are correct
        OrdersFileAccess fileAccess = new OrdersFileAccess();
        ArrayList<JsonObject> outputList = fileAccess.readItems(tempFile);
        assertEquals(itemList, outputList);
    }

    @Test
    public void test_ordersWriteItems() throws IOException, ParseException {
        // Create test objects
        JsonObject item1 = new JsonObject(new JsonObject.JsonBuilder(
                "1"
        )
        .setItemBarcode("1234556778890")
        .setQuantityPurchased(50)
        .setOrderDate(new Date(1609516215000L))
        .setCost(322.50));
        JsonObject item2 = new JsonObject(new JsonObject.JsonBuilder(
                "2"
        )
        .setItemBarcode("87380473583841")
        .setQuantityPurchased(500)
        .setOrderDate(new Date(1609459200000L))
        .setCost(906.10));
        ArrayList<JsonObject> itemList = new ArrayList<JsonObject>();
        itemList.add(item1);
        itemList.add(item2);
        File tempFile = folder.newFile("testData.json");

        // Call method to write objects to file
        OrdersFileAccess fileAccess = new OrdersFileAccess();
        fileAccess.writeItems(itemList, tempFile);

        // Read objects back from file and test they are correct
        Scanner scanner = new Scanner(tempFile);
        String fileData = scanner.next();
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject)parser.parse(fileData);
        JSONObject fileOutput1 = (JSONObject)jsonData.get("1");
        JSONObject fileOutput2 = (JSONObject)jsonData.get("2");
        ArrayList<JsonObject> outputList = new ArrayList<JsonObject>();
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput1.get("1"))
        .setItemBarcode((String)fileOutput1.get("barcode"))
        .setQuantityPurchased((int)fileOutput1.get("quantityPurchased"))
        .setOrderDate(new Date((long)fileOutput1.get("orderDate")))
        .setCost((float)fileOutput1.get("cost"))));
        outputList.add(new JsonObject(JsonObject.JsonBuilder((String)fileOutput2.get("1"))
        .setItemBarcode((String)fileOutput2.get("barcode"))
        .setQuantityPurchased((int)fileOutput2.get("quantityPurchased"))
        .setOrderDate(new Date((long)fileOutput2.get("orderDate")))
        .setCost((float)fileOutput1.get("cost"))));
        assertEquals(itemList, outputList);
    }

}
