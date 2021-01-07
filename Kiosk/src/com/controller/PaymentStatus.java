package com.controller;

public class PaymentStatus {
    // Simple data class for storing the result of a payment attempt
    public boolean success;
    public String message;

    public PaymentStatus(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
