package com.bookstore.app.entities.order.usecases;
import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetOrderByIdUseCase {
    @Autowired
    private IOrdersRepository ordersRepository;
    public Order handle(UUID id) {
        return ordersRepository.getOrderById(id);
    }
}
