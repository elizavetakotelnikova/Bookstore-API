package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.FeatureType;

import java.util.UUID;

public interface IFeatureTypesRepository {
    FeatureType saveFeatureType(FeatureType product);
    FeatureType findFeatureTypeById(UUID id);
    void deleteFeatureTypeById(UUID id);
}
