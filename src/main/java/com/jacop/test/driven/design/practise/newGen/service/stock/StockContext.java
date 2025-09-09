package com.jacop.test.driven.design.practise.newGen.service.stock;

import com.jacop.test.driven.design.practise.newGen.model.ProductType;
import com.jacop.test.driven.design.practise.newGen.model.exceptions.UnknownProductTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StockContext {

    private final Map<ProductType, StockStrategies> stockStrategiesMap;

    public String checkStock(ProductType productType) {
        StockStrategies stockStrategy = stockStrategiesMap.get(productType);
        if (stockStrategy == null) throw new UnknownProductTypeException("Unknown product type");
        return stockStrategy.checkStock();
    }

}
