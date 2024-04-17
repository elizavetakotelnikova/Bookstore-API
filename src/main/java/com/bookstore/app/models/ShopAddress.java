package com.bookstore.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shops")
public class ShopAddress {
    private String country;
    private String city;
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @Column(name="building_number")
    private int buildingNumber;
}
