package com.bookstore.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductType {
    private UUID id;
    private String name;

    public ProductType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

