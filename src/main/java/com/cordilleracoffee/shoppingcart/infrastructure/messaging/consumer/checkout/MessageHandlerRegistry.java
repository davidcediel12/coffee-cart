package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout;

import com.cordilleracoffee.shoppingcart.application.MessageHandler;
import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutEventType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandlerRegistry {


    private final Map<CheckoutEventType, MessageHandlerService> handlers = new EnumMap<>(CheckoutEventType.class);


    public MessageHandlerRegistry(List<MessageHandlerService> handlers) {

        handlers.forEach(handler -> {
            MessageHandler annotation = AnnotationUtils.findAnnotation(handler.getClass(), MessageHandler.class);
            assert annotation != null;

            Arrays.stream(annotation.events()).forEach(eventType -> {
                if(this.handlers.containsKey(eventType)) {
                    throw new IllegalStateException("Duplicated event handler for: " + eventType);
                }
                this.handlers.put(eventType, handler);
            });

        });

    }


    public MessageHandlerService getHandler(CheckoutEventType eventType) {
        return this.handlers.get(eventType);
    }
}
