package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CheckoutEventType {
    STOCK_RESERVATION_FAILED, PURCHASE_FAILED;

    @JsonCreator
    public static CheckoutEventType fromValue(String value) {
        try {
            return CheckoutEventType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
