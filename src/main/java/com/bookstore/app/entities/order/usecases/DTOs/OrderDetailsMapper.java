package com.bookstore.app.entities.order.usecases.DTOs;

import com.bookstore.app.entities.order.Order;
import org.springframework.stereotype.Service;


public class OrderDetailsMapper {
    public OrderDetailsDTO MapOrderToOrderDetails(Order order) {
        var totalPrice = order.calculateTotalPrice();
        return new OrderDetailsDTO(order.getId(), order.getUserId(),
                order.getDate(), order.getShopId(), order.getOrderState(), order.getProductList(),
                (int) totalPrice);
    }
}
