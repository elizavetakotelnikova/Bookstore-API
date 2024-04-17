package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.order.persistance.FindCriteria;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindUserByIdUseCase {
    private final IUsersRepository usersRepository;
    private final IOrdersRepository ordersRepository;
    public UserDetailsDTO handle(UUID id) {
        var user = usersRepository.findUserById(id);
        var orders = ordersRepository.findOrdersByCriteria(new FindCriteria(user.getId(), null));
        var ordersIds = new ArrayList<UUID>();
        for (var each : orders) ordersIds.add(each.getUser().getId());
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), ordersIds);
    }
}
