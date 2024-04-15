package com.bookstore.app.entities.product.usecases.productUseCases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetProductByIdUseCase {
    private final IProductsRepository productsRepository;
    public Product handle(UUID id) {
        return productsRepository.findProductById(id);
    }
}
