package com.bookstore.app.entities.order;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.valueObjects.OrderState;
import com.bookstore.app.valueObjects.OrderStateConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private UUID id;
    private UUID userId;
    private java.time.LocalDate date;
    private UUID shopId;
    @Enumerated(EnumType.ORDINAL)
    @Convert(converter = OrderStateConverter.class)
    private OrderState orderState;
    private List<Product> productList = new ArrayList<>();

    public Order(UUID userId, java.time.LocalDate date, UUID shopId, OrderState orderState){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = orderState;
    }

    public Order(UUID id, UUID userId, java.time.LocalDate date, UUID shopId, OrderState state) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = state;
    }
    public Order(UUID userId, java.time.LocalDate date, UUID shopId, OrderState state, List<Product> productList) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = state;
        this.productList = productList;
    }

    public float calculateTotalPrice() {
        return productList.stream().mapToInt(Product::getPrice).sum();
    }
}