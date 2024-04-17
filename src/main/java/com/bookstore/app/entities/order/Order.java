package com.bookstore.app.entities.order;
import com.bookstore.app.configuration.PostgreSQLEnumType;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.user.User;
import com.bookstore.app.valueObjects.OrderState;
import com.bookstore.app.valueObjects.OrderStateConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private UUID id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    private java.time.LocalDate date;
    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.EAGER)
    @JoinColumn(name="shop_id", nullable = false)
    private Shop shop;
    @Convert(converter = OrderStateConverter.class)
    @Column(name="order_state")
    @ColumnTransformer(write="?::order_state")
    private OrderState orderState;
    @ManyToMany(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_content",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> productList = new ArrayList<>();

    public Order(User user, java.time.LocalDate date, Shop shop, OrderState orderState){
        this.id = UUID.randomUUID();
        this.user = user;
        this.date = date;
        this.shop = shop;
        this.orderState = orderState;
    }

    public Order(UUID id, User user, java.time.LocalDate date, Shop shop, OrderState state) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.shop = shop;
        this.orderState = state;
    }
    public Order(User user, java.time.LocalDate date, Shop shop, OrderState state, List<Product> productList) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.date = date;
        this.shop = shop;
        this.orderState = state;
        this.productList = productList;
    }

    public float calculateTotalPrice() {
        return productList.stream().mapToInt(Product::getPrice).sum();
    }
}