package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindUsersByCriteriaUsecase {
    @Autowired
    private IUsersRepository usersRepository;

    public List<User> Handle(FindCriteria criteria) {
        return usersRepository.findUserByCriteria(criteria);
    }
}
