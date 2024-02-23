package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteShopUsesCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public void handle(UUID id) {
        shopsRepository.deleteShopById(id);
    }
}
