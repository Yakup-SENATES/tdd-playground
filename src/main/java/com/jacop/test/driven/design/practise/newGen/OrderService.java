package com.jacop.test.driven.design.practise.newGen;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;
import com.jacop.test.driven.design.practise.newGen.model.PlaceOrderRequest;
import com.jacop.test.driven.design.practise.newGen.model.ProductType;
import com.jacop.test.driven.design.practise.newGen.service.payment.CreditCardService;
import com.jacop.test.driven.design.practise.newGen.service.payment.PaymentContext;
import com.jacop.test.driven.design.practise.newGen.service.payment.PaymentStrategies;
import com.jacop.test.driven.design.practise.newGen.service.stock.BookStockService;
import com.jacop.test.driven.design.practise.newGen.service.stock.StockContext;
import com.jacop.test.driven.design.practise.newGen.service.stock.StockStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public static void main(String[] args) {
        Map<ProductType, StockStrategies> stockStrategiesMap = new HashMap<>();
        stockStrategiesMap.put(ProductType.BOOK, new BookStockService());
        StockContext stockContext = new StockContext(stockStrategiesMap);

        Map<PaymentMethod, PaymentStrategies> paymentStrategiesMap = new HashMap<>();
        paymentStrategiesMap.put(PaymentMethod.CREDIT_CARD, new CreditCardService());

        PaymentContext paymentContext = new PaymentContext(paymentStrategiesMap);

        OrderService orderService = new OrderService(stockContext, paymentContext);
        orderService.placeOrder(PlaceOrderRequest
                .builder()
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .quantity(1)
                .productType(ProductType.BOOK)
                .build()
        );
        System.out.println("its done");

    }
}