package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.persistance.IProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCase {
    @Autowired
    private IProductsRepository productsRepository;
    public void handle(UUID id) {
        productsRepository.deleteProductById(id);
    }
}
