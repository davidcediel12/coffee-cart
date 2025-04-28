package com.cordilleracoffee.shoppingcart.infrastructure.api.client.impl;


import com.cordilleracoffee.infrastructure.api.client.pricing.PricingApi;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ProductsValidation;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.PricingService;
import com.cordilleracoffee.shoppingcart.infrastructure.api.client.exception.ApiCallException;
import com.cordilleracoffee.shoppingcart.infrastructure.api.client.mappers.PricingMapper;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.error.ApiErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PricingApi pricingApi;
    private final PricingMapper pricingMapper;

    @Override
    public ValidationResponse validateShoppingCart(ShoppingCart shoppingCart) {

        ProductsValidation validation = pricingMapper.toProductsValidation(shoppingCart);

        try {
            return pricingApi.validatePost(validation);
        } catch (RestClientResponseException e) {
            String message = "Error calling pricing service while validating shopping cart";
            ApiErrorResponse errorResponse = buildErrorResponse(e, message);
            throw new ApiCallException(message, errorResponse, e);
        }
    }

    private static ApiErrorResponse buildErrorResponse(RestClientResponseException e, String message) {
        ApiErrorResponse errorResponse;
        try {
            errorResponse = e.getResponseBodyAs(ApiErrorResponse.class);
        } catch (RestClientException conversionException) {
            errorResponse = new ApiErrorResponse(LocalDateTime.now(), "SC-AC-01", message,
                    ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString());

        }
        return errorResponse;
    }

}
