package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import com.bookstore.app.entities.user.usecases.commands.CreateUserCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private HashingConfigure hashingConfigure;
    public boolean validateUser(CreateUserCommand command) {
        if (command.getPassword() == null || command.getPhoneNumber() == null) {
            return false;
        }
        return true;
    }
    public UserDetailsDTO handle(CreateUserCommand command) throws IncorrectArgumentsException {
        if (!validateUser(command)) throw new IncorrectArgumentsException("cannot create user, invalid data");
        var hashedPassword = hashingConfigure.Hash(command.getPassword());
        var user = new User(command.getPhoneNumber(), hashedPassword, command.getBalance(),
                command.getBirthday(), command.getOrdersHistory());
        user = usersRepository.saveUser(user);
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), user.getOrdersHistory());
    }
}
