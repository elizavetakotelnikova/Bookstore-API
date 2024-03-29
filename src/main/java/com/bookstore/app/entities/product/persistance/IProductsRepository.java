package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductsRepository {
    Product saveProduct(Product product);
    Product findProductById(UUID id);
    List<Product> findProductsByCriteria(FindCriteria criteria);

    Product updateProduct(Product product);
    void deleteProductById(UUID id);
}
