package com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository;

import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCart, UUID> {

    @Query("select sp from ShoppingCart sp where sp.status = 'ACTIVE' and sp.id = :id and sp.userId = :userId")
    Optional<ShoppingCart> findActiveByIdAndUserId(UUID id, String userId);
}