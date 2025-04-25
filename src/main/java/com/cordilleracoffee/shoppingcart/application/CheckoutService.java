package com.cordilleracoffee.shoppingcart.application;

import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;

import java.util.UUID;

public interface CheckoutService {
    ShoppingCart checkout(UUID cartId, String userId);
}
