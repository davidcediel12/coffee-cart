package com.cordilleracoffee.shoppingcart.application.impl;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.AppliedDiscount;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidatedItem;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.application.CheckoutService;
import com.cordilleracoffee.shoppingcart.application.exception.CartNotFoundException;
import com.cordilleracoffee.shoppingcart.application.exception.InvalidCartException;
import com.cordilleracoffee.shoppingcart.domain.model.CartItem;
import com.cordilleracoffee.shoppingcart.domain.model.ItemDiscount;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.PricingService;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CheckoutCartServiceImpl implements CheckoutService {

    private final ShoppingCartRepository cartRepository;
    private final PricingService pricingService;


    @Transactional
    @Override
    public ShoppingCart checkout(UUID cartId, String userId) {

        ShoppingCart cart = cartRepository.findActiveByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new CartNotFoundException("Active Cart not found with id: " + cartId + " for user: " + userId));

        ValidationResponse validationResponse = pricingService.validateShoppingCart(cart);

        if (Objects.equals(Boolean.FALSE, validationResponse.getIsValid())) {
            throw new InvalidCartException("Cart is invalid", validationResponse, cart);
        }

        completeDiscountInformation(validationResponse, cart);

        cart.process();
        return cartRepository.save(cart);
    }

    private void completeDiscountInformation(ValidationResponse validationResponse, ShoppingCart cart) {


        Map<String, ValidatedItem> validatedItems = new HashMap<>();

        Map<String, CartItem> cartItems = new HashMap<>();

        cart.getItems()
                .forEach(cartItem -> {
                    String itemKey = cartItem.getProductId() + Optional.ofNullable(cartItem.getVariantId())
                            .map(String::valueOf)
                            .map(variantId -> "_" + variantId)
                            .orElse("");
                    cartItems.put(itemKey, cartItem);
                });

        Optional.ofNullable(validationResponse.getItems()).orElse(Collections.emptyList())
                .forEach(item -> {
                    String itemKey = item.getProductId() + Optional.ofNullable(item.getVariantId())
                            .map(String::valueOf)
                            .map(variantId -> "_" + variantId)
                            .orElse("");

                    validatedItems.put(itemKey, item);
                });


        cartItems.forEach((key, cartItem) -> processItemDiscount(key, cartItem, validatedItems));

    }

    private void processItemDiscount(String itemKey, CartItem cartItem, Map<String, ValidatedItem> validatedItems) {
        if (cartItem.getDiscount() != null) {
            return;
        }
        ValidatedItem validatedItem = validatedItems.get(itemKey);
        AppliedDiscount appliedDiscount = validatedItem.getAppliedDiscount();
        if (appliedDiscount == null) {
            return;
        }

        ItemDiscount itemDiscount = new ItemDiscount();
        itemDiscount.setDiscountId(appliedDiscount.getDiscountId());
        itemDiscount.setDiscountAmount(Optional.ofNullable(appliedDiscount.getDiscountedAmount())
                .map(BigDecimal::valueOf)
                .orElse(BigDecimal.ZERO));

        itemDiscount = cartRepository.save(itemDiscount);
        cartItem.setDiscount(itemDiscount);
        cartRepository.save(cartItem);
    }
}



