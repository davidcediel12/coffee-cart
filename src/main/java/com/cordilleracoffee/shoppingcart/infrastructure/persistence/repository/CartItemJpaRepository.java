package com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository;

import com.cordilleracoffee.shoppingcart.domain.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemJpaRepository extends JpaRepository<CartItem, UUID> {
}