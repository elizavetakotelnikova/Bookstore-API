package com.bookstore.app.entities.order.usecases.DTOs;

import com.bookstore.app.entities.order.Order;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

public class OrderDetailsMapper {
    public OrderDetailsDTO MapOrderToOrderDetails(Order order) {
        var totalPrice = order.calculateTotalPrice();
        return new OrderDetailsDTO(order.getId(), order.getUser().getId(),
                order.getDate(), order.getShop().getId(), order.getOrderState(), order.getProductList(),
                (int) totalPrice);
    }
}
