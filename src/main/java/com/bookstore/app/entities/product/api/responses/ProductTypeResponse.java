package com.bookstore.app.entities.product.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductTypeResponse {
    private UUID id;
    private String name;
}
