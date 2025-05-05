package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto;

import java.util.UUID;

public record PurchaseFailed(UUID cartId) {}
