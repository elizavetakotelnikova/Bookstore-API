package com.bookstore.app.entities.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {
    private UUID id;
    private String name;

    public ProductType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

