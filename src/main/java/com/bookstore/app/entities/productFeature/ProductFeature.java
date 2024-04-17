package com.bookstore.app.entities.productFeature;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="feature_value")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeature {
    @Id
    private UUID id;
    @ManyToOne(targetEntity = FeatureType.class, fetch = FetchType.EAGER)
    @JoinColumn(name="feature_type_id", nullable = false)
    private FeatureType type;
    private String value;
    public ProductFeature(FeatureType type, String value) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.value = value;
    }
}
