package com.cordilleracoffee.shoppingcart.infrastructure.api.client.impl;


import com.cordilleracoffee.infrastructure.api.client.pricing.PricingApi;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ProductsValidation;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.PricingService;
import com.cordilleracoffee.shoppingcart.infrastructure.api.client.mappers.PricingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PricingApi pricingApi;
    private final PricingMapper pricingMapper;

    @Override
    public ValidationResponse validateShoppingCart(ShoppingCart shoppingCart) {

        ProductsValidation validation = pricingMapper.toProductsValidation(shoppingCart);

        return pricingApi.validatePost(validation);
    }

}
