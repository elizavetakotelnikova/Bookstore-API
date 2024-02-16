package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProductByIdUseCase {
    @Autowired
    private IProductsRepository productsRepository;
    public Product handle(UUID id) {
        return productsRepository.getProductById(id);
    }
}
