package com.cordilleracoffee.shoppingcart.application.impl;

import com.cordilleracoffee.shoppingcart.application.MessageHandler;
import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutEventType;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutMessage;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.PurchaseFailed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@MessageHandler(events = {
        CheckoutEventType.STOCK_RESERVATION_FAILED,
        CheckoutEventType.PURCHASE_FAILED,
        CheckoutEventType.PRICE_MISMATCH
})
@Service
@RequiredArgsConstructor
@Slf4j
public class UnlockCartService implements MessageHandlerService {


    private final ObjectMapper objectMapper;
    private final ShoppingCartRepository cartRepository;

    @Override
    @Transactional
    public void processMessage(CheckoutMessage checkoutMessage) {
        PurchaseFailed purchaseFailed;
        try {
            purchaseFailed = objectMapper.treeToValue(checkoutMessage.content(), PurchaseFailed.class);
        } catch (JsonProcessingException e) {

            log.error("Error processing checkout message", e);
            throw new RuntimeException(e);
        }

        ShoppingCart cart = cartRepository.findById(purchaseFailed.cartId())
                .orElseThrow(() -> new IllegalStateException("Cart not found: " + purchaseFailed.cartId()));

        cart.unlock();
        cartRepository.save(cart);

        log.info("Cart with id {} was unlocked", cart.getId());
    }
}
