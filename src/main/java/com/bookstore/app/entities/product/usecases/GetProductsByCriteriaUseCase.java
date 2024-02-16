package com.bookstore.app.entities.product.usecases;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.FindCriteria;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductsByCriteriaUseCase {
    @Autowired
    private IProductsRepository productsRepository;
    public List<Product> handle(FindCriteria criteria) {
        return productsRepository.findProductsByCriteria(criteria);
    }
}
