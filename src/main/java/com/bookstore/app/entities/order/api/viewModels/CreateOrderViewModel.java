package com.bookstore.app.entities.order.api.viewModels;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateOrderViewModel {
    private UUID userId;
    private Date date;
    private UUID shopId;
    private OrderState orderState;
    private List<Product> productList;
}
