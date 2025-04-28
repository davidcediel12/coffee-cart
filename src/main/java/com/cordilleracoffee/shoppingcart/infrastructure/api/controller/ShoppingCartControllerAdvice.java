package com.cordilleracoffee.shoppingcart.infrastructure.api.controller;


import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.application.exception.CartNotFoundException;
import com.cordilleracoffee.shoppingcart.application.exception.InvalidCartException;
import com.cordilleracoffee.shoppingcart.domain.model.CartStatus;
import com.cordilleracoffee.shoppingcart.infrastructure.api.client.exception.ApiCallException;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.ApiErrorResponse;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.AppliedDiscount;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.InvalidCartResponse;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.InvalidItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class ShoppingCartControllerAdvice {

    @ExceptionHandler(InvalidCartException.class)
    public ResponseEntity<InvalidCartResponse> handleInvalidCart(InvalidCartException e) {
        ValidationResponse pricingResponse = e.getValidationResponse();

        InvalidItem invalidItem = Optional.ofNullable(pricingResponse.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .filter(item -> Boolean.FALSE.equals(item.getIsValid()))
                .findFirst()
                .map(item -> new InvalidItem(
                        item.getProductId().longValue(),
                        Optional.ofNullable(item.getVariantId()).map(BigDecimal::longValue).orElse(null),
                        Optional.ofNullable(item.getFinalPrice()).map(BigDecimal::valueOf).orElse(null),
                        Optional.ofNullable(item.getAppliedDiscount())
                                .map(d ->
                                        new AppliedDiscount(d.getDiscountId(),
                                                Optional.ofNullable(d.getDiscountedAmount())
                                                        .map(BigDecimal::valueOf)
                                                        .orElse(null))),
                        "PRICE_MISMATCH"
                ))
                .orElse(null);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new InvalidCartResponse(
                        "Cart validation failed due to pricing mismatches",
                        Instant.from(pricingResponse.getValidatedAt()),
                        CartStatus.ACTIVE,
                        invalidItem,
                        "CART_VALIDATION_FAILED"
                ));
    }


    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCartNotFound(CartNotFoundException e) {

        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), "SC-NF-01",
                e.getMessage(), getCurrentUriString()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ApiCallException.class)
    public ResponseEntity<ApiErrorResponse> handleApiCallException(ApiCallException e) {

        log.error(e.getMessage(), e.getApiException());
        return new ResponseEntity<>(e.getErrorResponse(), e.getApiException().getStatusCode());
    }


    private static String getCurrentUriString() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    }
}
