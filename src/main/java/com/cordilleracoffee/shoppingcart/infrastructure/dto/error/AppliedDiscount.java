package com.cordilleracoffee.shoppingcart.infrastructure.dto.error;

import java.math.BigDecimal;
import java.util.UUID;

public record AppliedDiscount(
        UUID discountId,
        BigDecimal discountedAmount
) {}