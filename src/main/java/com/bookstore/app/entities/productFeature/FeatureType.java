package com.bookstore.app.entities.productFeature;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FeatureType {
    private UUID id;
    private String name;

    public FeatureType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

