package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.order.persistance.FindCriteria;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class FindUserByIdUseCase {
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private IOrdersRepository ordersRepository;

    public UserDetailsDTO handle(UUID id) {
        var user = usersRepository.findUserById(id);
        var orders = ordersRepository.findOrdersByCriteria(new FindCriteria(user.getId(), null));
        var ordersIds = new ArrayList<UUID>();
        for (var each : orders) ordersIds.add(each.getUserId());
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), ordersIds);
    }
}
