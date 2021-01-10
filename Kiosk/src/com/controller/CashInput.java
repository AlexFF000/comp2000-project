package com.controller;

public class CashInput implements IPaymentSystem{
    @Override
    public PaymentStatus HandlePayment(float amount){
        return new PaymentStatus(true, "Change given: £5.50");
    }
}
