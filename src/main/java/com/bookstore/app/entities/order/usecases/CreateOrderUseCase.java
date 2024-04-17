package com.bookstore.app.entities.order.usecases;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsMapper;
import com.bookstore.app.entities.order.usecases.commands.CreateOrderCommand;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateOrderUseCase {
    private final IOrdersRepository ordersRepository;
    private final IProductsRepository productsRepository;
    private final IShopsRepository shopsRepository;
    private final IUsersRepository usersRepository;
    private final OrderDetailsMapper mapper;
    public OrderDetailsDTO handle(CreateOrderCommand command) {
        var products = new ArrayList<Product>();
        for (UUID productId : command.getProductList()) {
            products.add(productsRepository.findProductById(productId));
        }
        var shop = shopsRepository.findShopById(command.getShopId());
        var user = usersRepository.findUserById(command.getUserId());
        var order = new Order(user, command.getDate(),
                shop, OrderState.CREATED, products);
        order = ordersRepository.saveOrder(order);
        return mapper.MapOrderToOrderDetails(order);
    }
}
