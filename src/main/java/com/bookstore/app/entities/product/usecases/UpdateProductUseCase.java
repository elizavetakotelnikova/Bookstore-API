package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCase {
    @Autowired
    private IProductsRepository productsRepository;
    public Product handle(UpdateProductCommand command) {
        var product = new Product(command.getType(), command.getName(),
                command.getPrice(), command.getFeatures());
        return productsRepository.updateProduct(product);
    }
}
