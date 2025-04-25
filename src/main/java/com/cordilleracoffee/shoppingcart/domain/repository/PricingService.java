package com.cordilleracoffee.shoppingcart.domain.repository;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;

public interface PricingService {
    ValidationResponse validateShoppingCart(ShoppingCart shoppingCart);
}
