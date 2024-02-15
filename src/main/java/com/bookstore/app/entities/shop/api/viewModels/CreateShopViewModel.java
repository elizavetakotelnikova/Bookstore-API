package com.bookstore.app.entities.shop.api.viewModels;

import com.bookstore.app.models.ShopAddress;
import lombok.Data;

@Data
public class CreateShopViewModel {
    private ShopAddress address;
}
