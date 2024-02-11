package com.bookstore.app.entities.product;

import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Product {
    private UUID id;
    private ProductType type;
    private String name;
    private int price;
    private List<ProductFeature> features;

    public Product(ProductType type, String name, int price, List<ProductFeature> features) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.features = features;
    }
    public Product(UUID id, ProductType type, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}