package com.bookstore.app.entities.shop;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    public UUID id;
    public ShopAddress address;
    public Shop(ShopAddress address) {
        this.id = UUID.randomUUID();
        this.address = address;
    }
}
