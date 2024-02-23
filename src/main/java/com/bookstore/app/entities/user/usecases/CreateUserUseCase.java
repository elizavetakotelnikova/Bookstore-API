package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import com.bookstore.app.entities.user.usecases.commands.CreateUserCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        if (!validateUser(command)) throw new IncorrectArgumentsException("Cannot create user, invalid data");
        if (!usersRepository.findUserByCriteria(new FindCriteria(command.getPhoneNumber(), null)).isEmpty()) {
            throw new IncorrectArgumentsException("User with same phone number already exists");
        }
        var hashedPassword = hashingConfigure.Hash(command.getPassword());
        var user = new User(command.getPhoneNumber(), hashedPassword, command.getBalance(),
                command.getBirthday());
        user = usersRepository.saveUser(user);
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), new ArrayList<>());
    }
}
