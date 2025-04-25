package com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.impl;

import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.ShoppingCartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {

    private final ShoppingCartJpaRepository cartJpaRepository;

    @Override
    public Optional<ShoppingCart> findActiveByIdAndUserId(UUID id, String userId) {
        return cartJpaRepository.findActiveByIdAndUserId(id, userId);
    }

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        return cartJpaRepository.save(cart);
    }
}
