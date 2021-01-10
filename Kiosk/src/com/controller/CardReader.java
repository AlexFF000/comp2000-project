package com.controller;

public class CardReader implements IPaymentSystem{
    @Override
    public PaymentStatus HandlePayment(float amount){
        return new PaymentStatus(true, "Payment successful");
    }
}
