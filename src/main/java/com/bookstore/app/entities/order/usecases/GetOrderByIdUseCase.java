package com.bookstore.app.entities.order.usecases;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetOrderByIdUseCase {
    @Autowired
    private IOrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsMapper mapper;
    public OrderDetailsDTO handle(UUID id) {
        var order = ordersRepository.findOrderById(id);
        return mapper.MapOrderToOrderDetails(order);
    }
}
