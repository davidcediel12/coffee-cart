package com.cordilleracoffee.shoppingcart.application.impl;

import com.cordilleracoffee.shoppingcart.application.MessageHandler;
import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutEventType;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutMessage;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.StockReservationFailed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@MessageHandler(event = CheckoutEventType.STOCK_RESERVATION_FAILED)
@Service
@RequiredArgsConstructor
@Slf4j
public class UnlockCartService implements MessageHandlerService {


    private final ObjectMapper objectMapper;
    private final ShoppingCartRepository cartRepository;

    @Override
    @Transactional
    public void processMessage(CheckoutMessage checkoutMessage) {
        StockReservationFailed stockReservationFailed;
        try {
            stockReservationFailed = objectMapper.treeToValue(checkoutMessage.content(), StockReservationFailed.class);
        } catch (JsonProcessingException e) {

            log.error("Error processing checkout message", e);
            throw new RuntimeException(e);
        }

        ShoppingCart cart = cartRepository.findById(stockReservationFailed.cartId())
                .orElseThrow(() -> new IllegalStateException("Cart not found: " + stockReservationFailed.cartId()));

        cart.unlock();
        cartRepository.save(cart);

        log.info("Cart with id {} was unlocked", cart.getId());
    }
}
