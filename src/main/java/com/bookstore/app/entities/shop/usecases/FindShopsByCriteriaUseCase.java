package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.FindCriteria;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindShopsByCriteriaUseCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public List<Shop> handle(FindCriteria criteria) {
        return shopsRepository.findShopsByCriteria(criteria);
    }
}
