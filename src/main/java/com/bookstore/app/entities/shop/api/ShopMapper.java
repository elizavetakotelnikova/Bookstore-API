package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.ShopJsonResponse;
import com.bookstore.app.entities.shop.api.viewModels.CreateShopViewModel;

public class ShopMapper {
    public Shop MapShopViewModelToShop(CreateShopViewModel viewModel) {
        return new Shop(viewModel.getAddress());
    }
    public ShopJsonResponse MapShopToViewModel(Shop shop) {
       return new ShopJsonResponse(shop.getId(), shop.getAddress());
    }
}
