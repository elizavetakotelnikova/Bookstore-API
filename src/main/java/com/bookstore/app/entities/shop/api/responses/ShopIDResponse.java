package com.bookstore.app.entities.shop.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShopIDResponse {
    private UUID id;
}
