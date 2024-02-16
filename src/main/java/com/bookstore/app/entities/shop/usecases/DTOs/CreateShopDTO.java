package com.bookstore.app.entities.shop.usecases.DTOs;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateShopDTO {
    public ShopAddress address;
}
