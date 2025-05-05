package com.cordilleracoffee.shoppingcart.application;


import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutMessage;

public interface MessageHandlerService {

    void processMessage(CheckoutMessage checkoutMessage);
}
