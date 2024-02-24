package com.bookstore.app.entities.productFeature;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductFeature {
    private UUID id;
    private FeatureType type;
    private String value;

    public ProductFeature(FeatureType type, String value) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.value = value;
    }
}
