package com.jacop.test.driven.design.practise.newGen.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {
    private ProductType productType;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
    private PaymentMethod paymentMethod;
}
