package com.jacop.test.driven.design.practise.newGen.service.payment;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService implements PaymentStrategies {
    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CREDIT_CARD;
    }

    @Override
    public String takePayment() {
        return "Paid with credit card";
    }
}
