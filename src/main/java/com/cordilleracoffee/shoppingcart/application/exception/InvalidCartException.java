package com.cordilleracoffee.shoppingcart.application.exception;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import lombok.Getter;

@Getter
public class InvalidCartException extends RuntimeException {

    private final ValidationResponse validationResponse;
    private final ShoppingCart cart;

    public InvalidCartException(String message,
                                ValidationResponse validationResponse, ShoppingCart cart) {
        super(message);
        this.validationResponse = validationResponse;
        this.cart = cart;
    }
}
