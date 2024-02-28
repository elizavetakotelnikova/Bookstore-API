package com.bookstore.app.entities.product.usecases.productUseCases;

import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteProductUseCase {
    private IProductsRepository productsRepository;
    public void handle(UUID id) throws QueryException {
        if (productsRepository.findProductById(id) == null) throw new QueryException("There is no such product");
        productsRepository.deleteProductById(id);
    }
}
