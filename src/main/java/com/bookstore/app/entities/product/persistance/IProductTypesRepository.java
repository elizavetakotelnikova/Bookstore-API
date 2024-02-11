package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.ProductType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductTypesRepository {
    ProductType save(ProductType productType);
    ProductType getProductTypeById(UUID id);
    void deleteProductTypeById(UUID id);
    ProductType updateProductTypeNameById(UUID id, String newName);
}
