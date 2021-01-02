package model;

import java.util.Date;

public class JsonDirector {

    // Create JsonObjects from model objects
    public static JsonObject BuildJsonUser(User user){
        // For users, the key is the username
        JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(user.getUsername())
                .setPassword(user.getPassword());
        return new JsonObject(builder);
    }

    public static JsonObject BuildJsonStockItem(StockItem item){
        // For StockItems, the key is the barcode
        JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(item.getBarcode())
                .setName(item.getName())
                .setQuantityInStock(item.getQuantityInStock())
                .setReorderLevel(item.getReorderLevel())
                .setSalePrice(item.getSalePrice())
                .setSupplierPrice(item.getSupplierPrice());
        return new JsonObject(builder);
    }

    public static JsonObject BuildJsonOrder(Order order){
        // For Orders, the key is the OrderID
        JsonObject.JsonBuilder builder = new JsonObject.JsonBuilder(order.getOrderID())
                .setItemBarcode(order.getBarcode())
                .setQuantityPurchased(order.getQuantityPurchased())
                .setOrderDate(order.getOrderDate())
                .setCost(order.getCost());
        return new JsonObject(builder);
    }

    // Create model objects from JsonObjects
    public static User JsonToUser(JsonObject jsonUser){
        return new User(
                jsonUser.getKey(),
                jsonUser.getPassword());
    }

    public static StockItem JsonToStockItem(JsonObject jsonItem){
        return new StockItem(
                jsonItem.getKey(),
                jsonItem.getName(),
                jsonItem.getQuantityInStock(),
                jsonItem.getReorderLevel(),
                jsonItem.getSalePrice(),
                jsonItem.getSupplierPrice());
    }

    public static Order JsonToOrder(JsonObject jsonOrder){
        return new Order(
                jsonOrder.getKey(),
                jsonOrder.getItemBarcode(),
                jsonOrder.getQuantityPurchased(),
                jsonOrder.getOrderDate(),
                jsonOrder.getCost());
    }
}
