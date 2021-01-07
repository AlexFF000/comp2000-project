package com.model;

import java.util.Date;

public class JsonObject {
    private final String key;
    private String newKey;
    // Fields for User
    private String password;
    private String salt;
    // Fields for StockItem
    private String name;
    private int quantityInStock;
    private int reorderLevel;
    private float salePrice;
    private float supplierPrice;
    // Fields for Order
    private String itemBarcode;
    private int quantityPurchased;
    private Date orderDate;
    private float cost;

    public JsonObject(JsonBuilder builder){
        this.key = builder.key;
        this.newKey = builder.newKey;
        this.password = builder.password;
        this.salt = builder.salt;
        this.name = builder.name;
        this.quantityInStock = builder.quantityInStock;
        this.reorderLevel = builder.reorderLevel;
        this.salePrice = builder.salePrice;
        this.supplierPrice = builder.supplierPrice;
        this.itemBarcode = builder.itemBarcode;
        this.quantityPurchased = builder.quantityPurchased;
        this.orderDate = builder.orderDate;
        this.cost = builder.cost;
    }

    public String getKey(){
        return key;
    }

    public String getNewKey(){
        return newKey;
    }

    public String getPassword(){
        return password;
    }

    public String getSalt(){
        return salt;
    }

    public String getName(){
        return name;
    }

    public int getQuantityInStock(){
        return quantityInStock;
    }

    public int getReorderLevel(){
        return reorderLevel;
    }

    public float getSalePrice(){
        return salePrice;
    }

    public float getSupplierPrice(){
        return supplierPrice;
    }

    public String getItemBarcode(){
        return itemBarcode;
    }

    public int getQuantityPurchased(){
        return quantityPurchased;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public float getCost(){
        return cost;
    }

    public static class JsonBuilder{
        public final String key;
        public String newKey;
        public String password;
        public String salt;
        public String name;
        public int quantityInStock;
        public int reorderLevel;
        public float salePrice;
        public float supplierPrice;
        public String itemBarcode;
        public int quantityPurchased;
        public Date orderDate;
        public float cost;

        public JsonBuilder(String key){
            this.key = key;
        }

        public JsonBuilder setNewKey(String value){
            this.newKey = value;
            return this;
        }

        public JsonBuilder setPassword(String password){
            this.password = password;
            return this;
        }

        public JsonBuilder setSalt(String salt){
            this.salt = salt;
            return this;
        }

        public JsonBuilder setName(String name){
            this.name = name;
            return this;
        }

        public JsonBuilder setQuantityInStock(int quantity){
            this.quantityInStock = quantity;
            return this;
        }

        public JsonBuilder setReorderLevel(int level){
            this.reorderLevel = level;
            return this;
        }

        public JsonBuilder setSalePrice(float price){
            this.salePrice = price;
            return this;
        }

        public JsonBuilder setSupplierPrice(float price){
            this.supplierPrice = price;
            return this;
        }

        public JsonBuilder setItemBarcode(String barcode){
            this.itemBarcode = barcode;
            return this;
        }

        public JsonBuilder setQuantityPurchased(int quantity){
            this.quantityPurchased = quantity;
            return this;
        }

        public JsonBuilder setOrderDate(Date orderDate){
            this.orderDate = orderDate;
            return this;
        }

        public JsonBuilder setCost(float cost){
            this.cost = cost;
            return this;
        }
    }
}

