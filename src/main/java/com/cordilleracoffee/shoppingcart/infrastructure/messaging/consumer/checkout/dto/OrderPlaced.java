package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto;

import java.util.UUID;

public record OrderPlaced(CartId cartSnapshot) {

    public record CartId(UUID cartId){}
}
