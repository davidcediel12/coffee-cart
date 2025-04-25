package com.cordilleracoffee.shoppingcart.infrastructure.dto.response;

import com.cordilleracoffee.shoppingcart.domain.model.CartStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CheckoutResponse(
        UUID cartId,
        String userId,
        CartStatus status,
        String currency,
        Instant createdAt,
        List<CartItemResponse> items
) {}
