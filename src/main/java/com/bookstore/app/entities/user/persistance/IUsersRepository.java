package com.bookstore.app.entities.user.persistance;

import com.bookstore.app.entities.user.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUsersRepository {
    User save(User user);
    User getUserById(UUID id);

    User geUserByPhoneNumber(String phoneNumber);

    void updateUser(); //или апдейт user password
    void deleteUserById(UUID id);
}