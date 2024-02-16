package com.bookstore.app.entities.order;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Order {
    private UUID id;
    private UUID userId;
    private Date date;
    private UUID shopId;
    private OrderState orderState;
    private List<Product> productList;

    public Order(UUID userId, Date date, UUID shopId, OrderState orderState){
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = orderState;
    }

    public Order(UUID id, UUID userId, Date date, UUID shopId, OrderState state) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = state;
    }
    public Order(UUID userId, Date date, UUID shopId, OrderState state, List<Product> productList) {
        this.userId = userId;
        this.date = date;
        this.shopId = shopId;
        this.orderState = state;
    }

    public float calculateTotalPrice() {
        return productList.stream().mapToInt(Product::getPrice).sum();
    }
}