package com.bookstore.app.entities.shop.usecases.viewModels;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateShopCommand {
    public ShopAddress address;
}
