package com.cordilleracoffee.shoppingcart.infrastructure.dto.error;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ApiErrorResponse(LocalDateTime timestamp, String error, String message, String path) implements Serializable {
}
