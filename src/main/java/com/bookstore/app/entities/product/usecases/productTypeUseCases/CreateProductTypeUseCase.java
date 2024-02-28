package com.bookstore.app.entities.product.usecases.productTypeUseCases;

import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.product.persistance.IProductTypesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateProductTypeUseCase {
    private IProductTypesRepository productTypesRepository;
    public ProductType handle(String name) throws IncorrectArgumentsException {
        if (productTypesRepository.findProductTypeByName(name) != null)
            throw new IncorrectArgumentsException("Already exists");
        return productTypesRepository.saveProductType(new ProductType(name));
    }
}
