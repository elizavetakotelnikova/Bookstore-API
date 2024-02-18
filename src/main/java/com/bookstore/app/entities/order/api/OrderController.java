package com.bookstore.app.entities.order.api;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.order.api.responses.OrderIDResponse;
import com.bookstore.app.entities.order.api.responses.OrderResponse;
import com.bookstore.app.entities.order.api.viewModels.CreateOrderViewModel;
import com.bookstore.app.entities.order.api.viewModels.UpdateOrderViewModel;
import com.bookstore.app.entities.order.persistance.FindCriteria;
import com.bookstore.app.entities.order.usecases.*;
import com.bookstore.app.entities.order.usecases.DTOs.OrderDetailsDTO;
import com.bookstore.app.entities.order.usecases.commands.CreateOrderCommand;
import com.bookstore.app.entities.order.usecases.commands.UpdateOrderCommand;
import com.bookstore.app.exceptions.QueryException;
import org.aspectj.bridge.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {
    @Autowired
    private CreateOrderUseCase createOrderUseCase;
    @Autowired
    private GetOrderByIdUseCase getOrderByIdUseCase;
    @Autowired
    private GetOrdersByCriteriaUseCase getOrdersByCriteriaUsecase;
    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;
    @Autowired
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
    public OrderResponse getOrderById(@PathVariable("orderId") UUID orderId) {
        var order = getOrderByIdUseCase.handle(orderId);
        return new OrderResponse(order.getId(), order.getUserId(),
                order.getDate(), order.getShopId(), order.getOrderState(), order.getProductList(),
                order.getTotalPrice());

    }
    @GetMapping("/orders/")
    public List<OrderResponse> getOrderByCriteria(@Param("userId") UUID userId, @Param("date") LocalDate date) {
        var criteria = new FindCriteria();
        if (userId != null) criteria.setUserId(userId);
        if (date != null) criteria.setDate(date);
        var orders = getOrdersByCriteriaUsecase.handle(criteria);
        var ordersResponses = new ArrayList<OrderResponse>();
        for (OrderDetailsDTO each : orders) {
            ordersResponses.add(new OrderResponse(each.getId(), each.getUserId(),
                    each.getDate(), each.getShopId(), each.getOrderState(), each.getProductList(),
                    each.getTotalPrice()));
        }
        return ordersResponses;
    }

    @PutMapping("/order/{orderId}")
    public OrderIDResponse updateOrder(@PathVariable("orderId") UUID id, @RequestBody UpdateOrderViewModel providedOrder) {
        var command = new UpdateOrderCommand(providedOrder.getId(), providedOrder.getUserId(),
                providedOrder.getDate(), providedOrder.getShopId(), providedOrder.getOrderState(),
                providedOrder.getProductList());
        var order = updateOrderUseCase.handle(command);
        return new OrderIDResponse(order.getId());
    }

    @DeleteMapping("/order/{orderId}")
    public void updateOrder(@PathVariable("orderId") UUID id) {
        try {
            deleteOrderUseCase.handle(id);
        } catch (QueryException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}

