package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductsRepository {
    Product save(Product product);
    Product getProductById(UUID id);
    List<Product> findAllProductsByTypeId(UUID id);

    Product updateProduct(Product product);
    void deleteProductById(UUID id);
}
