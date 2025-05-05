package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto;

import java.util.UUID;

public record StockReservationFailed(UUID cartId) {}
