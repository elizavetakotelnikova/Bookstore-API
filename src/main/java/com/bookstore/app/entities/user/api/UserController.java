package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {
    private IUsersRepository usersRepository;
    @PostMapping("/users")
    public CreateResponseBody create(@RequestBody CreateUserViewModel providedUser) {
        var user = new User(providedUser.getPhoneNumber(), providedUser.getPassword(),
                 providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        usersRepository.save(user);
        return new CreateResponseBody(user.getId());
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return usersRepository.getUserById(userId);
    }
}
