package com.bookstore.app.entities.productFeature;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="feature_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureType {
    @Id
    private UUID id;
    private String name;

    public FeatureType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

