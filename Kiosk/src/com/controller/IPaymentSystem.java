package com.controller;

public interface IPaymentSystem {
    PaymentStatus HandlePayment(float amount);
}
