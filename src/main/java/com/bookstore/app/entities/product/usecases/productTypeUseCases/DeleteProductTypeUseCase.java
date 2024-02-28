package com.bookstore.app.entities.product.usecases.productTypeUseCases;

import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.product.persistance.IProductTypesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteProductTypeUseCase {
    private IProductTypesRepository productTypesRepository;
    public void handle(UUID id) throws QueryException {
        if (productTypesRepository.findProductTypeById(id) == null)
            throw new QueryException("No such product type");
        productTypesRepository.deleteProductTypeById(id);
    }
}
