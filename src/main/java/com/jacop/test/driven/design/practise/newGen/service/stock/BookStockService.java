package com.jacop.test.driven.design.practise.newGen.service.stock;

import com.jacop.test.driven.design.practise.newGen.model.ProductType;
import org.springframework.stereotype.Service;

@Service
public class BookStockService implements StockStrategies{

    @Override
    public ProductType productType() {
        return ProductType.BOOK;
    }

    @Override
    public String checkStock() {
        return "Checking stock for Book...";
    }
}
