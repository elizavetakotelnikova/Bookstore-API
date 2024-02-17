package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.ProductFeature;

import java.util.List;
import java.util.UUID;

public interface IProductFeaturesRepository {
    ProductFeature saveProductFeature(ProductFeature productFeature);
    ProductFeature findProductFeatureById(UUID id);
    List<ProductFeature> findAllFeaturesValuesByFeatureTypeId(UUID id);
    void deleteProductFeatureById(UUID id);
}
