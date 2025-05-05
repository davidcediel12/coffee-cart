package com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout;

import com.cordilleracoffee.shoppingcart.application.MessageHandler;
import com.cordilleracoffee.shoppingcart.application.MessageHandlerService;
import com.cordilleracoffee.shoppingcart.infrastructure.messaging.consumer.checkout.dto.CheckoutEventType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

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
            this.handlers.put(annotation.event(), handler);

        });

    }


    public MessageHandlerService getHandler(CheckoutEventType eventType) {
        return this.handlers.get(eventType);
    }
}
