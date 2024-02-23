package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.shop.usecases.DTOs.CreateShopDTO;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateShopUseCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public Shop handle(CreateShopDTO command) throws IncorrectArgumentsException {
        if (command.address == null) throw new IncorrectArgumentsException("Cannot create shop");
        var shop = new Shop(command.address);
        return shopsRepository.saveShop(shop);
    }
}
