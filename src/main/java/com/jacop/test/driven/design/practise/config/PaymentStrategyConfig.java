package com.jacop.test.driven.design.practise.config;

import com.jacop.test.driven.design.practise.newGen.model.PaymentMethod;
import com.jacop.test.driven.design.practise.newGen.service.payment.PaymentStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentStrategyConfig {

    private final List<PaymentStrategies> paymentStrategiesList;


    @Bean
    public Map<PaymentMethod, PaymentStrategies> getPaymentStrategiesMap() {
        Map<PaymentMethod, PaymentStrategies> paymentStrategiesMap = new EnumMap<>(PaymentMethod.class);
        paymentStrategiesList.forEach(p -> paymentStrategiesMap.put(p.getPaymentMethod(), p));
        return paymentStrategiesMap;
    }


}
