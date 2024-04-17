package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindUsersByCriteriaUseCase {
    private final IUsersRepository usersRepository;
    private final IOrdersRepository ordersRepository;
    public List<UserDetailsDTO> handle(FindCriteria criteria) {
        var users = usersRepository.findUserByCriteria(criteria);
        var usersDTO = new ArrayList<UserDetailsDTO>();
        for (User each : users) {
            var orders = ordersRepository.findOrdersByCriteria(new com.bookstore.app.entities.order.persistance.FindCriteria(each.getId(), null));
            var ordersIds = new ArrayList<UUID>();
            for (var eachOrder : orders) ordersIds.add(eachOrder.getUser().getId());
            usersDTO.add(new UserDetailsDTO(each.getId(), each.getPhoneNumber(),
                    each.getPassword(), each.getBalance(), each.getBirthday(), ordersIds));
        }
        return usersDTO;
    }
}
