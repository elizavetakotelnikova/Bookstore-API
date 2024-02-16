package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.commands.UpdateUserCommand;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUseCase {
    @Autowired
    private IUsersRepository usersRepository;
    private HashingConfigure hashingConfigure;
    public User handle(UpdateUserCommand command) {
        if (command.getBirthday() == null) {
            throw new RuntimeException("cannot update user, invalid password");
        }
        var hashedPassword = hashingConfigure.Hash(command.getPassword());
        return usersRepository.updateUser(new User(command.getId(), command.getPhoneNumber(),
                hashedPassword, command.getBalance(), command.getBirthday(), command.getOrdersHistory()));
    }
}
