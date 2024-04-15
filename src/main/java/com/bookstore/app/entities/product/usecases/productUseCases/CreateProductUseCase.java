package com.bookstore.app.entities.product.usecases.productUseCases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.product.usecases.commands.CreateProductCommand;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.product.persistance.IProductFeaturesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CreateProductUseCase {
    private final IProductsRepository productsRepository;
    private final IProductFeaturesRepository productFeaturesRepository;
    IFeatureTypesRepository featureTypesRepository;
    public Product handle(CreateProductCommand command) throws IncorrectArgumentsException {
        // validation
        if (command.getName() == null ||
        command.getType().getName() == null) throw new RuntimeException("Cannot create product");
        var productFeatures = new ArrayList<ProductFeature>();
        //getting features
        for (var each : command.getFeatures()) {
            var type = featureTypesRepository.findFeatureTypeById(each.a);
            if (type == null) throw new IncorrectArgumentsException("No such feature types");
            productFeatures.add(new ProductFeature(type, each.b));
        }
        //creating product entity
        var product = new Product(command.getType(), command.getName(), command.getPrice(), productFeatures);
        product = productsRepository.saveProduct(product);
        return product;
    }
}
