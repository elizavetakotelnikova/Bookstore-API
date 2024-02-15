package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.Product;

public class ProductMapper {
    public Product mapCreateViewModelToProduct(CreateProductViewModel productViewModel) {
        return new Product(productViewModel.getType(), productViewModel.getName(), productViewModel.getPrice(),
                productViewModel.getFeatures());
    }
}
