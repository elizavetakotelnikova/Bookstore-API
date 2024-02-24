package com.bookstore.app.entities.productFeature.persistance;

import com.bookstore.app.entities.productFeature.FeatureType;

import java.util.UUID;

public interface IFeatureTypesRepository {
    FeatureType saveFeatureType(FeatureType product);
    FeatureType findFeatureTypeById(UUID id);
    FeatureType findFeatureByName(String name);
    FeatureType updateFeatureType(FeatureType featureType);
    void deleteFeatureTypeById(UUID id);
}
