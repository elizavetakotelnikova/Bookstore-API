package com.bookstore.app.entities.shop.api.viewModels;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateShopViewModels {
    public ShopAddress address;
}
