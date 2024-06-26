package com.bookstore.app.entities.order.usecases;

import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteOrderUseCase {
    private final IOrdersRepository ordersRepository;
    public void handle(UUID id) throws QueryException {
        if (ordersRepository.findOrderById(id) == null) throw new QueryException("There is no such order");
        ordersRepository.deleteOrderById(id);
    }
}
