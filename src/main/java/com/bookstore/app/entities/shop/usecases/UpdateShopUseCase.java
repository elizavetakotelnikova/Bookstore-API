package com.bookstore.app.entities.shop.usecases;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.shop.usecases.DTOs.UpdateShopDTO;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateShopUseCase {
    @Autowired
    private IShopsRepository shopsRepository;
    public Shop handle(UpdateShopDTO command) throws IncorrectArgumentsException {
        if (command.address == null) throw new IncorrectArgumentsException("cannot update Shop");
        var shop = new Shop(command.getId(), command.getAddress());
        return shopsRepository.updateShop(shop);
    }

}
