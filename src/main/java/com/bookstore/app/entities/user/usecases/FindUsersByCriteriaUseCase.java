package com.bookstore.app.entities.user.usecases;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindUsersByCriteriaUseCase {
    @Autowired
    private IUsersRepository usersRepository;

    public List<UserDetailsDTO> handle(FindCriteria criteria) {
        var users = usersRepository.findUserByCriteria(criteria);
        var usersDTO = new ArrayList<UserDetailsDTO>();
        for (User each : users) {
            usersDTO.add(new UserDetailsDTO(each.getId(), each.getPhoneNumber(),
                    each.getPassword(), each.getBalance(), each.getBirthday(), each.getOrdersHistory()));
        }
        return usersDTO;
    }
}
