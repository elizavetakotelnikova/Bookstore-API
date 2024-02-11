package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.FeatureType;

import java.util.UUID;

public interface IFeatureTypesRepository {
    FeatureType save(FeatureType product);
    FeatureType getFeatureNameById(UUID id);
    void deleteFeatureTypeById(UUID id);
}