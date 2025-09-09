package com.jacop.test.driven.design.practise.newGen.model.exceptions;

public class UnknownProductTypeException extends RuntimeException {
    public UnknownProductTypeException(String productType) {
        super("Unknown product type: " + productType);
    }

    public UnknownProductTypeException(Enum<?> productType) {
        super("Unknown product type: " + productType);
    }
}
