package com.bookstore.app.entities.product;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.productFeature.ProductFeature;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private UUID id;
    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn(name="type_id", nullable = false)
    private ProductType type;
    private String name;
    private int price;
    @ManyToMany(targetEntity = ProductFeature.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_features",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "feature_value_id") }
    )
    private List<ProductFeature> features;
    public Product(ProductType type, String name, int price, List<ProductFeature> features) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.name = name;
        this.price = price;
        this.features = features;
    }
    public Product(UUID id, ProductType type, String name, int price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.features = new ArrayList<>();
    }

}
