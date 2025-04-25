package com.cordilleracoffee.shoppingcart.application.impl;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.ValidationResponse;
import com.cordilleracoffee.shoppingcart.application.CheckoutService;
import com.cordilleracoffee.shoppingcart.application.exception.CartNotFoundException;
import com.cordilleracoffee.shoppingcart.application.exception.InvalidCartException;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.PricingService;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CheckoutCartServiceImpl implements CheckoutService {

    private final ShoppingCartRepository cartRepository;
    private final PricingService pricingService;

    @Transactional
    @Override
    public ShoppingCart checkout(UUID cartId, String userId) {

        ShoppingCart cart = cartRepository.findActiveByIdAndUserId(cartId, userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId + " for user: " + userId));

        ValidationResponse validationResponse = pricingService.validateShoppingCart(cart);

        if(Objects.equals(Boolean.FALSE, validationResponse.getIsValid())){
            throw new InvalidCartException("Cart is invalid", validationResponse);
        }

        cart.process();
        return cartRepository.save(cart);
    }

}
