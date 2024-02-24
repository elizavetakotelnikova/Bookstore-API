package com.bookstore.app.entities.shop.dto;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateShopDTO {
    public ShopAddress address;
}
