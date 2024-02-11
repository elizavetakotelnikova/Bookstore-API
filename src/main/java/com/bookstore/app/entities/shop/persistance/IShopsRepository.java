package com.bookstore.app.entities.shop.persistance;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.models.ShopAddress;

import java.util.List;
import java.util.UUID;

public interface IShopsRepository {
    Shop save(Shop shop);
    Shop getShopById(UUID id);
    List<Shop> findAllShopsByCity(String city);
    void deleteShopById(UUID id);
    Shop update(Shop shop);
}
