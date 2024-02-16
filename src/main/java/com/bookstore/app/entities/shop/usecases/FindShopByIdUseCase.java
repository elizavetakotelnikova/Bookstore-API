package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindShopByIdUseCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public Shop handle(UUID id) {
        return shopsRepository.getShopById(id);
    }
}
