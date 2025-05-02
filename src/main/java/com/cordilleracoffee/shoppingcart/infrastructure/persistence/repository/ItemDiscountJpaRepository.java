package com.cordilleracoffee.shoppingcart.infrastructure.persistence.repository;

import com.cordilleracoffee.shoppingcart.domain.model.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemDiscountJpaRepository extends JpaRepository<ItemDiscount, UUID> {
}