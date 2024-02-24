package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.ProductType;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductTypesRepository {
    ProductType saveProductType(ProductType productType);
    ProductType findProductTypeById(UUID id);
    ProductType findProductTypeByName(String name);
    void deleteProductTypeById(UUID id);
    ProductType updateProductType(ProductType type);
}
