package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout;

import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckoutTopicListener {

    private final ObjectMapper objectMapper;
    private final MessageHandlerRegistry handlerRegistry;


    @KafkaListener(groupId = "cart", topics = "checkout")
    public void consume(GenericMessage<String> message) {

        log.info("Received Message from stock topic: {}", message.getPayload());
        CheckoutMessage checkoutMessage;
        try {
            checkoutMessage = objectMapper.readValue(message.getPayload(), CheckoutMessage.class);

            if (checkoutMessage.checkoutEventType() == null) {
                return;
            }

            MessageHandlerService handler = handlerRegistry.getHandler(checkoutMessage.checkoutEventType());

            if(handler == null){
                return;
            }

            handler.processMessage(checkoutMessage);

        } catch (JsonProcessingException e) {
            log.error("Unable to parse message", e);
            throw new RuntimeException(e);
        }
    }
}
