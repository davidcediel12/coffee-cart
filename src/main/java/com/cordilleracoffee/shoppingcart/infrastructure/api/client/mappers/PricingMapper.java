package com.cordilleracoffee.shoppingcart.infrastructure.api.client.mappers;

import com.cordilleracoffee.infrastructure.api.client.pricing.model.ItemValidation;
import com.cordilleracoffee.infrastructure.api.client.pricing.model.ProductsValidation;
import com.cordilleracoffee.shoppingcart.domain.model.CartItem;
import com.cordilleracoffee.shoppingcart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PricingMapper {


    @Mapping(target = "products", source = "items")
    ProductsValidation toProductsValidation(ShoppingCart shoppingCart);

    @Mapping(target = "discountId", source = "discount.discountId")
    @Mapping(target = "finalPrice", source = "finalBasePrice")
    @Mapping(target = "basePrice", source = "unitPrice")
    @Mapping(target = "couponCode", source = "discount.couponCode")
    ItemValidation toItemValidation(CartItem cartItem);
}
