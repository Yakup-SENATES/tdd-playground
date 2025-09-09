package com.jacop.test.driven.design.practise.newGen.service.stock;

import com.jacop.test.driven.design.practise.newGen.model.ProductType;
import org.springframework.stereotype.Service;

@Service
public class PhoneStockService implements StockStrategies{

    @Override
    public ProductType productType() {
        return ProductType.PHONE;
    }

    @Override
    public String checkStock() {
        return "Checking stock for Phone...";
    }
}
