package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserByIdUseCase {
    @Autowired
    private IUsersRepository usersRepository;

    public UserDetailsDTO handle(UUID id) {
        var user = usersRepository.findUserById(id);
        return new UserDetailsDTO(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(), user.getOrdersHistory());
    }
}
