package com.jacop.test.driven.design.practise.newGen.model.exceptions;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(UUID productId, int requested, int available) {
        super(String.format("Insufficient stock for the product %s: requested= %s: available=%s", productId, requested, available));
    }
}
