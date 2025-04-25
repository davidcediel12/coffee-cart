package com.cordilleracoffee.shoppingcart.infrastructure.dto.error;

import com.cordilleracoffee.shoppingcart.domain.model.CartStatus;

import java.time.Instant;

public record InvalidCartResponse(String message,
                                  Instant validatedAt,
                                  CartStatus cartStatus,
                                  InvalidItem firstInvalidItem,
                                  String errorCode
) {}