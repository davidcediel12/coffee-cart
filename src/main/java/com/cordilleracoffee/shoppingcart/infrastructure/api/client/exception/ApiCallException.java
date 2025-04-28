package com.cordilleracoffee.shoppingcart.infrastructure.api.client.exception;

import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.ApiErrorResponse;
import lombok.Getter;
import org.springframework.web.client.RestClientResponseException;

@Getter
public class ApiCallException extends RuntimeException {

    private final ApiErrorResponse errorResponse;

    public ApiCallException(String message, ApiErrorResponse errorResponse, RestClientResponseException apiException) {
        super(message);
        this.errorResponse = errorResponse;
        this.apiException = apiException;
    }

    private final RestClientResponseException apiException;


}
