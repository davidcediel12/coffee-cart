package com.cordilleracoffee.shoppingcart.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "item_discount")
public class ItemDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID discountId;
    private String couponCode;
    private DiscountType discountType;
    private BigDecimal discountAmount;
}