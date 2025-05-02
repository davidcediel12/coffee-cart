package com.cordilleracoffee.shoppingcart.infrastructure.dto.response;

import com.cordilleracoffee.shoppingcart.domain.model.CartStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CheckoutResponse(
        UUID cartId,
        String userId,
        CartStatus status,
        String currency,
        BigDecimal price,
        Instant createdAt,
        List<CartItemResponse> items
) {}
