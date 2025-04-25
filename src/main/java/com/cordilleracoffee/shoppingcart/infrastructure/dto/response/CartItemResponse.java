package com.cordilleracoffee.shoppingcart.infrastructure.dto.response;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record CartItemResponse(
        UUID itemId,
        Long productId,
        Long variantId,
        Long categoryId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal finalBasePrice,
        Optional<ItemDiscountResponse> discount
) {}
