package com.cordilleracoffee.shoppingcart.application.exception;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;

public class InvalidCartException extends RuntimeException {

    private final ValidationResponse validationResponse;

    public InvalidCartException(String message, ValidationResponse validationResponse) {
        super(message);
        this.validationResponse = validationResponse;
    }
}
