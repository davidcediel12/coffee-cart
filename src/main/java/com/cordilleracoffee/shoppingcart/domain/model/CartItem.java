package com.cordilleracoffee.shoppingcart.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;


    @ManyToOne
    private ShoppingCart shoppingCart;

    @OneToOne
    private ItemDiscount discount;

    @Column(nullable = false)
    private Long productId;

    private Long variantId;
    private Long categoryId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal finalBasePrice;
}