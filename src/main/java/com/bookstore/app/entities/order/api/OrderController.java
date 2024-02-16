package com.bookstore.app.entities.order.api;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.api.responses.OrderIDResponse;
import com.bookstore.app.entities.order.api.responses.OrderResponse;
import com.bookstore.app.entities.order.api.viewModels.CreateOrderViewModel;
import com.bookstore.app.entities.order.usecases.DTOs.CreateOrderDTO;
import com.bookstore.app.entities.order.usecases.CreateOrderUseCase;
import com.bookstore.app.entities.order.usecases.GetOrderByIdUseCase;
import com.bookstore.app.entities.order.usecases.commands.CreateOrderCommand;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {
    private CreateOrderUseCase createOrderUseCase;
    private GetOrderByIdUseCase getOrderByIdUseCase;
    private GetOrdersByCriteriaUseCase getOrdersByCriteriaUsecase;
    private UpdateOrderUseCase updateOrderUseCase;
    private DeleteOrderUseCase deleteOrderUseCase;
    @PostMapping("/orders")
    public OrderResponse createOrder(@RequestBody CreateOrderViewModel providedOrder) throws InvalidKeySpecException {
        var command = new CreateOrderCommand(providedOrder.getUserId(), providedOrder.getDate(), providedOrder.getShopId(),
                providedOrder.getOrderState(), providedOrder.getProductList());
        var createOrderDTO = createOrderUseCase.handle(command);
        return new OrderResponse(createOrderDTO.getId(), createOrderDTO.getUserId(),
                createOrderDTO.getDate(), createOrderDTO.getShopId(), createOrderDTO.getOrderState(), createOrderDTO.getProductList(),
                createOrderDTO.getTotalPrice());
    }

    @GetMapping("/order/{orderId}")
    public Order getOrderById(@PathVariable("orderId") UUID orderId) {
        return getOrderByIdUseCase.handle(orderId);
    }
    @GetMapping("/orders/")
    public List<Order> getOrderByCriteria(@Param("typeId") UUID typeId, @Param("name") String name) {
        var criteria = new FindCriteria(null, null);
        if (typeId != null) criteria.setTypeId(typeId);
        if (name != null) criteria.setName(name);

        return getOrdersByCriteriaUsecase.Handle(criteria);
    }

    @PutMapping("/order/{orderId}")
    public OrderIDResponse updateOrder(@PathVariable("orderId") UUID id, @RequestBody CreateOrderViewModel providedOrder) {
        var command = new UpdateOrderCommand(providedOrder.getType(),
                providedOrder.getName(), providedOrder.getPrice(), providedOrder.getFeatures());
        var order = updateOrderUseCase.handle(command);
        return new OrderIDResponse(order.getId());
    }

    @DeleteMapping("/order/{orderId}")
    public void updateOrder(@PathVariable("orderId") UUID id) {
        deleteOrderUseCase.handle(id);
    }
}

