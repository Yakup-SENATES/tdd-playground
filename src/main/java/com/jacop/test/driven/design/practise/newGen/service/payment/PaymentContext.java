package com.jacop.test.driven.design.practise.newGen.service.payment;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentContext {

    private final Map<PaymentMethod, PaymentStrategies> paymentStrategiesMap;

    public String takePayment(PaymentMethod paymentMethod) {
        PaymentStrategies paymentStrategy = paymentStrategiesMap.get(paymentMethod);
        if (paymentStrategy == null) throw new IllegalArgumentException("Unsupported payment method");
        return paymentStrategy.takePayment();
    }

}
