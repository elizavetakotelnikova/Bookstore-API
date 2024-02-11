package com.bookstore.app.entities.order.persistance;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.valueObjects.OrderState;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IOrdersRepository {
    Order save(Order order);
    Order getOrderById(UUID id);
    List<Order> findAllOrdersByUserId(UUID id);
    List<Order> findAllOrdersByDate(Date date);
    void deleteOrderById(UUID id);
    //Order updateOrdersStateById(UUID id, OrderState newOrderState);

    @Transactional
    Order updateOrder(Order order);
}
