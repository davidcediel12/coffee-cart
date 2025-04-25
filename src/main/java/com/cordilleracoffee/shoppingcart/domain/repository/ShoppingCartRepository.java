package com.cordilleracoffee.shoppingcart.domain.repository;

import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository {

    Optional<ShoppingCart> findActiveByIdAndUserId(UUID id, String userId);
}
