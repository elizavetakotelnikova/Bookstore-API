package com.bookstore.app.entities.shop.api.responses;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ShopDetailsResponse {
    public UUID id;
    public ShopAddress address;
}
