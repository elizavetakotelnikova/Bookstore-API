package com.bookstore.app.entities.shop;

import com.bookstore.app.models.ShopAddress;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @Id
    public UUID id;
    @Embedded
    public ShopAddress address;
    public Shop(ShopAddress address) {
        this.id = UUID.randomUUID();
        this.address = address;
    }
}
