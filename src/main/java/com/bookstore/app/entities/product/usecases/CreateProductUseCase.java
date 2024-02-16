package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.product.usecases.commands.CreateProductCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {
    private IProductsRepository productsRepository;
    public Product handle(CreateProductCommand command) {
        if (command.getName() == null ||
        command.getType().getName() == null) throw new RuntimeException("Cannot create product");
        var product = new Product(command.getType(), command.getName(), command.getPrice(), command.getFeatures());
        product = productsRepository.save(product);
        return product;
    }
}
