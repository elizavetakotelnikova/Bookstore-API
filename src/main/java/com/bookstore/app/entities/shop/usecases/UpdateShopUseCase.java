package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateShopUseCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public Shop handle(UpdateShopCommand command) {
        if (command.address == null) throw new RuntimeException("cannot update Shop");
        var shop = new Shop(command.getId(), command.getAddress());
        return shopsRepository.update(shop);
    }

}
