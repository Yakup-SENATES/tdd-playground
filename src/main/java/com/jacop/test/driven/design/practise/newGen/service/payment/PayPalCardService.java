package com.jacop.test.driven.design.practise.newGen.service.payment;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class PayPalCardService implements PaymentStrategies {
    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.PAYPAL;
    }

    @Override
    public String takePayment() {
        return "Paid with PayPal";
    }
}
