package com.bookstore.app.entities.product.usecases.productTypeUseCases;

import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.product.persistance.IProductTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductTypeByNameUseCase {
    @Autowired
    private IProductTypesRepository productTypesRepository;
    public ProductType handle(String name) {
        return productTypesRepository.findProductTypeByName(name);
    }
}
