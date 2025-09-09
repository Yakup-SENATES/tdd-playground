package com.jacop.test.driven.design.practise.newGen;

import com.jacop.test.driven.design.practise.newGen.model.PlaceOrderRequest;
import com.jacop.test.driven.design.practise.newGen.service.payment.PaymentContext;
import com.jacop.test.driven.design.practise.newGen.service.stock.StockContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
    for SOLID OOP AND CLEAN CODE demonstration
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final StockContext stockContext;
    private final PaymentContext paymentContext;

    public void placeOrder(PlaceOrderRequest placeOrderRequest) {

        // stock kontrol
        String stockStatus = stockContext.checkStock(placeOrderRequest.getProductType());
        System.out.println(stockStatus);


        // ödeme
        String paymentStatus = paymentContext.takePayment(placeOrderRequest.getPaymentMethod());
        System.out.println(paymentStatus);

        // siparişi kaydet

        System.out.println("Order placed for " + placeOrderRequest.getQuantity() + " " + placeOrderRequest.getProductType());
    }
}