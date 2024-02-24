package com.bookstore.app.entities.product.usecases.productTypeUseCases;

import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.product.persistance.IProductTypesRepository;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductTypeCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductTypeUseCase {
    @Autowired
    private IProductTypesRepository productTypesRepository;
    public ProductType handle(UpdateProductTypeCommand command) throws IncorrectArgumentsException {
        if (productTypesRepository.findProductTypeByName(command.getName()) == null)
            throw new IncorrectArgumentsException("No such product type");
        return productTypesRepository.updateProductType(new ProductType(command.getId(), command.getName()));
    }
}
