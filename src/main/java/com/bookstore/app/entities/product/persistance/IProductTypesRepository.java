package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.ProductType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductTypesRepository {
    ProductType saveProductType(ProductType productType);
    ProductType findProductTypeById(UUID id);
    void deleteProductTypeById(UUID id);
    ProductType updateProductType(ProductType type);
}
