package com.bookstore.app.entities.shop.persistance;

import com.bookstore.app.entities.shop.Shop;

import java.util.List;
import java.util.UUID;

public interface IShopsRepository {
    Shop saveShop(Shop shop);
    Shop findShopById(UUID id);
    List<Shop> findShopsByCriteria(FindCriteria criteria);
    void deleteShopById(UUID id);
    Shop updateShop(Shop shop);
}
