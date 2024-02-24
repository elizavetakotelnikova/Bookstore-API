package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.ShopDetailsResponse;
import com.bookstore.app.entities.shop.dto.CreateShopDTO;

public class ShopMapper {
    public Shop MapShopViewModelToShop(CreateShopDTO viewModel) {
        return new Shop(viewModel.getAddress());
    }
    public ShopDetailsResponse MapShopToViewModel(Shop shop) {
       return new ShopDetailsResponse(shop.getId(), shop.getAddress());
    }
}
