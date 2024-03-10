package com.bookstore.app.entities.product.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeResponse {
    private UUID id;
    private String name;
}
