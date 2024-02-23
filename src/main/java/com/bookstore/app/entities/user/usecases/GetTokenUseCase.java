package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.stereotype.Service;

@Service
public class GetTokenUseCase {
    IUsersRepository usersRepository;
    HashingConfigure hashingConfigure;
    public String validatePasswordFromDB(String passwordFromUser, String phoneNumber) {
        var criteria = new FindCriteria(phoneNumber, null);
        var users = usersRepository.findUserByCriteria(criteria);
        if (users.size() != 1) throw new RuntimeException("Wrong response from a database");
        if (users.getFirst().getPassword() != hashingConfigure.Hash(passwordFromUser)) {
            throw new SecurityException("Wrong password");
        }
        return null; //should return a JWT
    }
}
