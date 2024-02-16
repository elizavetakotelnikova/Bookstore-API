package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.usecases.commands.CreateUserCommand;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    @Autowired
    private HashingConfigure hashingConfigure;
    public boolean validateUser(CreateUserCommand command) {
        if (command.getPassword() == null || command.getPhoneNumber() == null) {
            return false;
        }
        return true;
    }
    public User handle(CreateUserCommand command) {
        if (!validateUser(command)) throw new RuntimeException("cannot create user, invalid data");
        var hashedPassword = hashingConfigure.Hash(command.getPassword());
        return new User(command.getPhoneNumber(), hashedPassword, command.getBalance(),
                command.getBirthday(), command.getOrdersHistory());
    }
}
