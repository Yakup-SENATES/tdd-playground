package com.jacop.test.driven.design.practise.newGen.model;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceOrderRequest {
    private ProductType productType;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
    private PaymentMethod paymentMethod;
}
//curl -X POST http://localhost:8080 \
//     -H "Content-Type: application/json" \
//     -d '{"name": "ChatGPT", "age": 3}'