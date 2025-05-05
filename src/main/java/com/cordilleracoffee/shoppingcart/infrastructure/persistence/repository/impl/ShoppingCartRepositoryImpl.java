package com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.impl;

import com.cordilleracoffee.shoppingcart.domain.model.CartItem;
import com.cordilleracoffee.shoppingcart.domain.model.ItemDiscount;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.CartItemJpaRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.ItemDiscountJpaRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository.ShoppingCartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {

    private final ShoppingCartJpaRepository cartJpaRepository;
    private final ItemDiscountJpaRepository itemDiscountJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;

    @Override
    public Optional<ShoppingCart> findActiveByIdAndUserId(UUID id, String userId) {
        return cartJpaRepository.findActiveByIdAndUserId(id, userId);
    }

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        return cartJpaRepository.save(cart);
    }


    @Override
    public ItemDiscount save(ItemDiscount itemDiscount){
        return itemDiscountJpaRepository.save(itemDiscount);
    }


    @Override
    public CartItem save(CartItem cartItem){
        return cartItemJpaRepository.save(cartItem);
    }

    @Override
    public Optional<ShoppingCart> findById(UUID cartId) {
        return cartJpaRepository.findById(cartId);
    }
}
