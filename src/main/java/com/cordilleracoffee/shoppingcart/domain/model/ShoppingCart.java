package com.cordilleracoffee.shoppingcart.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
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
    private CartStatus status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shoppingCart")
    private Set<CartItem> items;

}