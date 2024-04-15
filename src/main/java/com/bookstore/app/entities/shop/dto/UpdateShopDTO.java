package com.bookstore.app.entities.shop.dto;

import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShopDTO {
    public ShopAddress address;
}
