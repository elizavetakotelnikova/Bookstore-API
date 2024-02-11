package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.ProductFeature;

import java.util.List;
import java.util.UUID;

public interface IFeatureValuesRepository {
    ProductFeature save(ProductFeature productFeature);
    ProductFeature getProductFeatureByFeatureId(UUID id);
    List<ProductFeature> findAllFeaturesValuesByFeatureTypeId(UUID id);
    void deleteFeatureByFeatureId(UUID id);
}
