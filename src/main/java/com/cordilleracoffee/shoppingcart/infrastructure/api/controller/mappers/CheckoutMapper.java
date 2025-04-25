package com.cordilleracoffee.shoppingcart.infrastructure.api.controller.mappers;

import com.cordilleracoffee.shoppingcart.domain.model.CartItem;
import com.cordilleracoffee.shoppingcart.domain.model.ItemDiscount;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.response.CartItemResponse;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.response.CheckoutResponse;
import com.cordilleracoffee.shoppingcart.infrastructure.dto.response.ItemDiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {
    CheckoutResponse toDto(ShoppingCart cart);

    @Mapping(target = "discount", source = "discount")
    CartItemResponse toDto(CartItem item);

    ItemDiscountResponse toDto(ItemDiscount discount);

    default Optional<ItemDiscountResponse> mapDiscount(ItemDiscount discount) {
        return Optional.ofNullable(discount)
                .map(this::toDto);
    }
}
