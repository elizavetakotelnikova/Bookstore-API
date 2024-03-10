package com.bookstore.app.entities.order.usecases;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetOrderByIdUseCase {
    private IOrdersRepository ordersRepository;
    private OrderDetailsMapper mapper;
    public OrderDetailsDTO handle(UUID id) {
        var order = ordersRepository.findOrderById(id);
        return mapper.MapOrderToOrderDetails(order);
    }
}
