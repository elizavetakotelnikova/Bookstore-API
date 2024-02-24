package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductCommand;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpdateProductUseCase {
    @Autowired
    private IProductsRepository productsRepository;
    @Autowired
    private IFeatureTypesRepository featureTypesRepository;
    public Product handle(UpdateProductCommand command) throws IncorrectArgumentsException {
        var productFeatures = new ArrayList<ProductFeature>();
        //getting features from command
        for (var each : command.getFeatures()) {
            var type = featureTypesRepository.findFeatureTypeById(each.a);
            if (type == null) throw new IncorrectArgumentsException("No such feature types");
            productFeatures.add(new ProductFeature(type, each.b));
        }
        //updating product
        var product = new Product(command.getType(), command.getName(),
                command.getPrice(), productFeatures);
        return productsRepository.updateProduct(product);
    }
}
