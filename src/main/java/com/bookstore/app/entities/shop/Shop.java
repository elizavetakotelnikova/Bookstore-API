package com.bookstore.app.entities.shop;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Shop {
    public long id;
    public ShopAddress address;
}
