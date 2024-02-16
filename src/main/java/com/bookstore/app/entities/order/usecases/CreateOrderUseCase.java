package com.bookstore.app.entities.order.usecases;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.CreateOrderDTO;
import com.bookstore.app.entities.order.usecases.commands.CreateOrderCommand;
import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateOrderUseCase {
    @Autowired
    private IOrdersRepository ordersRepository;
    public CreateOrderDTO handle(CreateOrderCommand command) {
        var order = new Order(command.getUserId(), command.getDate(),
                command.getShopId(), OrderState.Created, command.getProductList());
        var totalPrice = order.calculateTotalPrice();
        ordersRepository.save(order);
        return new CreateOrderDTO(order.getId(), order.getUserId(),
        order.getDate(), order.getShopId(), order.getOrderState(), order.getProductList(),
                (int) totalPrice);
    }
}
