package com.bookstore.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopAddress {
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int buildingNumber;
}
