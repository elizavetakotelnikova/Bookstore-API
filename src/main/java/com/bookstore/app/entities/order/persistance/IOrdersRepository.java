package com.bookstore.app.entities.order.persistance;

import com.bookstore.app.entities.order.Order;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrdersRepository {
    Order saveOrder(Order order);
    Order findOrderById(UUID id);
    List<Order> findOrdersByCriteria(FindCriteria criteria);
    void deleteOrderById(UUID id);
    //Order updateOrdersStateById(UUID id, OrderState newOrderState);
    @Transactional
    Order updateOrder(Order order);
}
