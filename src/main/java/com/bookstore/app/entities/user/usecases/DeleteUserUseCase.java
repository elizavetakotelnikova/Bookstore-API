package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.persistance.IUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteUserUseCase {
    private IUsersRepository usersRepository;

    public void handle(UUID id) {
        if (usersRepository.findUserById(id) == null) throw new RuntimeException("There is no such user");
        usersRepository.deleteUserById(id);
    }
}
