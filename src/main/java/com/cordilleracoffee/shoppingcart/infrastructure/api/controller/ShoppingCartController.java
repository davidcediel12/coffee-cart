package com.cordilleracoffee.shoppingcart.infrastructure.api.controller;


import com.cordilleracoffee.shoppingcart.application.CheckoutService;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.infrastructure.api.controller.mappers.CheckoutMapper;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.response.CheckoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CheckoutService checkoutService;
    private final CheckoutMapper checkoutMapper;



    @PostMapping("/{cartId}/checkout")
    @ResponseStatus(HttpStatus.OK)
    public CheckoutResponse checkout(@PathVariable("cartId") UUID cartId,
                                     @RequestHeader("App-User-ID") String userId) {

        ShoppingCart shoppingCart = checkoutService.checkout(cartId, userId);

        return checkoutMapper.toDto(shoppingCart);
    }
}
