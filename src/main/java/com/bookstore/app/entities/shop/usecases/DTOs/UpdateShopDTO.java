package com.bookstore.app.entities.shop.usecases.DTOs;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateShopDTO {
    public UUID id;
    public ShopAddress address;
}
