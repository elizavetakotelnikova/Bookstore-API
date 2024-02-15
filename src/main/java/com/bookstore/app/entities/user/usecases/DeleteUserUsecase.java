package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUsecase {
    private IUsersRepository usersRepository;

    public void Handle(UUID id) {
        usersRepository.deleteUserById(id);
    }
}
