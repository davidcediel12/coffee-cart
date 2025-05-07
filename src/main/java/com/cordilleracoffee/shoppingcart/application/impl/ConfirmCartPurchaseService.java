package com.cordilleracoffee.shoppingcart.application.impl;

import com.cordilleracoffee.shoppingcart.application.MessageHandler;
import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.domain.repository.ShoppingCartRepository;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutEventType;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutMessage;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.OrderPlaced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@MessageHandler(events = {CheckoutEventType.ORDER_PLACED})
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmCartPurchaseService implements MessageHandlerService {


    private final ObjectMapper objectMapper;
    private final ShoppingCartRepository cartRepository;


    @Override
    @Transactional
    public void processMessage(CheckoutMessage checkoutMessage) {
        OrderPlaced orderPlaced;

        try {
            orderPlaced = objectMapper.treeToValue(checkoutMessage.content(), OrderPlaced.class);
        } catch (JsonProcessingException e) {
            log.error("Unable to parse message", e);
            throw new RuntimeException(e);
        }

        cartRepository.findById(orderPlaced.cartSnapshot().cartId())
                .ifPresent(cart -> {
                    cart.confirmPurchase();
                    cartRepository.save(cart);
                });

        log.info("Check out completed for cartId: {}", orderPlaced.cartSnapshot().cartId());
    }
}
