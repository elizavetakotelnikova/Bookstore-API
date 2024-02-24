package com.bookstore.app.entities.shop.dto;

import com.bookstore.app.models.ShopAddress;
import lombok.Data;

@Data
public class CreateShopDTO {
    private ShopAddress address;
}
