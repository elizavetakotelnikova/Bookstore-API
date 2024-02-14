package com.bookstore.app.entities.user.persistance;

import com.bookstore.app.entities.user.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IUsersRepository {
    User save(User user);
    User findUserById(UUID id);
    List<User> findUserByCriteria(FindCriteria criteria);
    User updateUser(User user);
    void deleteUserById(UUID id);
}