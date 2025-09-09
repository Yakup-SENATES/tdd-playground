package com.jacop.test.driven.design.practise.newGen.service.payment;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;

public interface PaymentStrategies {

    PaymentMethod getPaymentMethod();

    String takePayment();

}
