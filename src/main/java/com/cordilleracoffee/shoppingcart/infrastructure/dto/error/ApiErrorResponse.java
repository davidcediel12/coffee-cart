package com.cordilleracoffee.shoppingcart.infrastructure.dto.error;

import java.time.LocalDateTime;

public record ApiErrorResponse(LocalDateTime timestamp, String error, String message, String path) {
}
