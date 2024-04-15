package com.bookstore.app.entities.product.usecases.productUseCases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.FindCriteria;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProductsByCriteriaUseCase {
    private final IProductsRepository productsRepository;
    public List<Product> handle(FindCriteria criteria) {
        return productsRepository.findProductsByCriteria(criteria);
    }
}
