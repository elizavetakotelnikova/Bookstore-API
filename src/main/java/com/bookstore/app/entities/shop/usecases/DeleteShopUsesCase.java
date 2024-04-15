package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteShopUsesCase {
    private final IShopsRepository shopsRepository;
    public void handle(UUID id) {
        shopsRepository.deleteShopById(id);
    }
}
