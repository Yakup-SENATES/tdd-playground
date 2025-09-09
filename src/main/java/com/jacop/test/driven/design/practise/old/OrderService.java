package com.jacop.test.driven.design.practise.old;

/*
    for SOLID OOP AND CLEAN CODE demonstration
 */
public class OrderService {

    public void placeOrder(String productType, int quantity, String paymentMethod) {
        // validation
        if (quantity <= 0) {
            System.out.println("Invalid quantity");
            return;
        }

        // stock kontrol
        if (productType.equals("Book")) {
            System.out.println("Checking stock for Book...");
        } else if (productType.equals("Phone")) {
            System.out.println("Checking stock for Phone...");
        } else {
            System.out.println("Unknown product type");
            return;
        }

        // ödeme
        if (paymentMethod.equals("CREDIT_CARD")) {
            System.out.println("Paid with credit card");
        } else if (paymentMethod.equals("PAYPAL")) {
            System.out.println("Paid with PayPal");
        } else {
            System.out.println("Unsupported payment method");
            return;
        }

        // siparişi kaydet
        System.out.println("Order placed for " + quantity + " " + productType);
    }
}