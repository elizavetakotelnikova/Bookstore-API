package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserByIdUsecase {
    private IUsersRepository usersRepository;

    public User Handle(UUID id) {
        return usersRepository.findUserById(id);
    }
}
