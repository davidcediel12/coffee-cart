package com.cordilleracoffee.shoppingcart.infrastructure.dto.response;

import com.cordilleracoffee.shoppingcart.domain.model.DiscountType;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDiscountResponse(
        UUID discountId,
        String couponCode,
        DiscountType discountType,
        BigDecimal discountAmount
) {}
