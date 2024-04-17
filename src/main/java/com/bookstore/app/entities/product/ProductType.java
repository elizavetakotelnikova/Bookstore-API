package com.bookstore.app.entities.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.UUID;

@Entity
@Table(name="product_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {
    @Id
    private UUID id;
    private String name;

    public ProductType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

