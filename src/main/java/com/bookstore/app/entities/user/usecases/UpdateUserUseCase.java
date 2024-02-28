package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.order.persistance.FindCriteria;
import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import com.bookstore.app.entities.user.usecases.commands.UpdateUserCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.infrastructure.HashingConfigure;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateUserUseCase {
    private IUsersRepository usersRepository;
    private IOrdersRepository ordersRepository;
    private HashingConfigure hashingConfigure;
    public UserDetailsDTO handle(UpdateUserCommand command) throws IncorrectArgumentsException {
        if (command.getBirthday() == null) {
            throw new IncorrectArgumentsException("cannot update user, invalid password");
        }
        var hashedPassword = hashingConfigure.Hash(command.getPassword());
        var user = new User(command.getPhoneNumber(), hashedPassword, command.getBalance(),
                command.getBirthday());
        user = usersRepository.updateUser(user);
        var orders = ordersRepository.findOrdersByCriteria(new FindCriteria(user.getId(), null));
        var ordersIds = new ArrayList<UUID>();
        for (var each : orders) ordersIds.add(each.getUserId());
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), ordersIds);
    }
}
