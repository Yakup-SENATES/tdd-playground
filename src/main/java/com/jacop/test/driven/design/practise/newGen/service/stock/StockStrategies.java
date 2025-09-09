package com.jacop.test.driven.design.practise.newGen.service.stock;

import com.jacop.test.driven.design.practise.newGen.model.ProductType;

public interface StockStrategies {

    ProductType productType();

    String checkStock();
}
