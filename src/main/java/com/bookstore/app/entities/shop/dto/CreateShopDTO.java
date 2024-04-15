package com.bookstore.app.entities.shop.dto;

import com.bookstore.app.models.ShopAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateShopDTO {
    private ShopAddress address;
}
