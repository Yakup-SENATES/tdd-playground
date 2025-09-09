package com.jacop.test.driven.design.practise.config;

import com.jacop.test.driven.design.practise.newGen.model.ProductType;
import com.jacop.test.driven.design.practise.newGen.service.stock.StockStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StockStrategyConfig {

    private final List<StockStrategies> stockStrategies;

    @Bean
    public Map<ProductType, StockStrategies> fillStockStrategiesMap() {
        Map<ProductType, StockStrategies> stockStrategiesMap = new EnumMap<>(ProductType.class);
        stockStrategies.forEach(s -> stockStrategiesMap.put(s.productType(), s));
        return stockStrategiesMap;
    }


}
