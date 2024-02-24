package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.ShopDetailsResponse;
import com.bookstore.app.entities.shop.api.viewModels.CreateShopViewModel;

public class ShopMapper {
    public Shop MapShopViewModelToShop(CreateShopViewModel viewModel) {
        return new Shop(viewModel.getAddress());
    }
    public ShopDetailsResponse MapShopToViewModel(Shop shop) {
       return new ShopDetailsResponse(shop.getId(), shop.getAddress());
    }
}
