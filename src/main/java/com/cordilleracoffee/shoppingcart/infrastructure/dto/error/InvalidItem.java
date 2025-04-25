package com.cordilleracoffee.shoppingcart.infrastructure.dto.error;

import java.math.BigDecimal;
import java.util.Optional;

public record InvalidItem(
        Long productId,
        Long variantId,
        BigDecimal expectedPrice,
        Optional<AppliedDiscount> appliedDiscount,
        String reason
) {
}