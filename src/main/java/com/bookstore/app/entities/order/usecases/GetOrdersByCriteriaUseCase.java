package com.bookstore.app.entities.order.usecases;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.persistance.FindCriteria;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdersByCriteriaUseCase {
    @Autowired
    private IOrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsMapper mapper;
    public List<OrderDetailsDTO> handle(FindCriteria criteria) {
        var orders = ordersRepository.findOrdersByCriteria(criteria);
        var ordersDTO = new ArrayList<OrderDetailsDTO>();
        for (Order each : orders) {
            ordersDTO.add(mapper.MapOrderToOrderDetails(each));
        }
        return ordersDTO;
    }
}
