package com.cordilleracoffee.shoppingcart.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;


    private String userId;

    private UUID sessionId;

    @Enumerated(EnumType.STRING)
    private CartStatus status;
    private String currency;
    private BigDecimal price;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shoppingCart")
    private Set<CartItem> items;



    public void process(){
        if(!status.equals(CartStatus.ACTIVE)){
            throw new IllegalStateException("Cart that is no active cannot be checked out");
        }

        this.price = Optional.ofNullable(this.items).orElse(Collections.emptySet())
                .stream()
                .map(CartItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.status = CartStatus.PROCESSING;
    }

}