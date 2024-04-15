package com.bookstore.app.entities.order.usecases;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsMapper;
import com.bookstore.app.entities.order.usecases.commands.CreateOrderCommand;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateOrderUseCase {
    private IOrdersRepository ordersRepository;
    private IProductsRepository productsRepository;
    private OrderDetailsMapper mapper;
    public OrderDetailsDTO handle(CreateOrderCommand command) {
        var products = new ArrayList<Product>();
        for (UUID productId : command.getProductList()) {
            products.add(productsRepository.findProductById(productId));
        }
        var order = new Order(command.getUserId(), command.getDate(),
                command.getShopId(), OrderState.CREATED, products);
        order = ordersRepository.saveOrder(order);
        return mapper.MapOrderToOrderDetails(order);
    }
}
