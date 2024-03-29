package tests.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.controller.*;
import com.model.*;
import com.view.*;

@RunWith(MockitoJUnitRunner.class)
public class Test_checkoutController {

    @Test
    public void test_scanItems_itemsExist() throws NoSuchFieldException, IllegalAccessException {
        StockManager stockManager = StockManager.getInstance();
        StockItem item1 = new StockItem("123456789101112", "Test Item 1", 10, 5, 12.00f, 10.52f);
        StockItem item2 = new StockItem("123456789101113", "Test Item 2", 31, 5, 12.00f, 10.52f);
        StockItem item3 = new StockItem("123456789101114", "Test Item 3", 56, 5, 12.00f, 10.52f);
        StockItem item4 = new StockItem("123456789101115", "Test Item 4", 16, 5, 12.00f, 10.52f);
        Map<String, StockItem> stockDict = new HashMap<String, StockItem>();
        stockDict.put("123456789101112", item1);
        stockDict.put("123456789101113", item2);
        stockDict.put("123456789101114", item3);
        stockDict.put("123456789101115", item4);
        stockManager.stock = stockDict;
        CheckoutController controller = new CheckoutController();
        controller.setView(new CheckoutView(null));
        ArrayList<StockItem> testList = new ArrayList<StockItem>();
        testList.add(item2);
        testList.add(item4);
        testList.add(item2);
        controller.scanItem("123456789101113");
        controller.scanItem("123456789101115");
        controller.scanItem("123456789101113");

        Field basketField = controller.getClass().getDeclaredField("basket");
        basketField.setAccessible(true);
        ArrayList<StockItem> actualList = (ArrayList<StockItem>)basketField.get(controller);
        assertEquals(testList, actualList);
    }

    @Test
    public void test_scanItems_itemDoesNotExist() throws NoSuchFieldException, IllegalAccessException {
        StockManager stockManager = StockManager.getInstance();
        StockItem item1 = new StockItem("123456789101112", "Test Item 1", 10, 5, 12.00f, 10.52f);
        StockItem item2 = new StockItem("123456789101113", "Test Item 2", 31, 5, 12.00f, 10.52f);
        Map<String, StockItem> stockDict = new HashMap<String, StockItem>();
        stockDict.put("123456789101112", item1);
        stockDict.put("123456789101113", item2);
        stockManager.stock = stockDict;
        CheckoutController controller = new CheckoutController();
        controller.setView(new CheckoutView(null));
        ArrayList<StockItem> testList = new ArrayList<StockItem>();
        testList.add(item1);
        testList.add(item2);
        controller.scanItem("123456789101112");
        // The resulting list should not contain this item, as it does not exist
        controller.scanItem("123456789101115");
        controller.scanItem("123456789101113");

        Field basketField = controller.getClass().getDeclaredField("basket");
        basketField.setAccessible(true);
        ArrayList<StockItem> actualList = (ArrayList<StockItem>)basketField.get(controller);
        assertEquals(testList, actualList);
    }

    @Test
    public void test_removeScannedItem() throws NoSuchFieldException, IllegalAccessException {
        StockManager stockManager = StockManager.getInstance();
        StockItem item1 = new StockItem("123456789101112", "Test Item 1", 10, 5, 12.00f, 10.52f);
        StockItem item2 = new StockItem("123456789101113", "Test Item 2", 31, 5, 12.00f, 10.52f);
        Map<String, StockItem> stockDict = new HashMap<String, StockItem>();
        stockDict.put("123456789101112", item1);
        stockDict.put("123456789101113", item2);
        stockManager.stock = stockDict;
        CheckoutController controller = new CheckoutController();
        controller.setView(new CheckoutView(null));
        ArrayList<StockItem> testList = new ArrayList<StockItem>();
        testList.add(item1);
        Field basketField = controller.getClass().getDeclaredField("basket");
        basketField.setAccessible(true);
        basketField.get(controller);
        ArrayList<StockItem> actualList = (ArrayList<StockItem>)basketField.get(controller);
        actualList.add(item1);
        actualList.add(item2);
        controller.removeScannedItem("123456789101113");

        assertEquals(testList, actualList);
    }
}
