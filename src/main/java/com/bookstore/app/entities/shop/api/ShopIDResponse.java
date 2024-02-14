package com.bookstore.app.entities.shop.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShopIDResponse {
    private UUID id;
}
