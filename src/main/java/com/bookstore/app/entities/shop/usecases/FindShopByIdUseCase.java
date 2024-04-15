package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindShopByIdUseCase {
    private final IShopsRepository shopsRepository;
    public Shop handle(UUID id) {
        return shopsRepository.findShopById(id);
    }
}
