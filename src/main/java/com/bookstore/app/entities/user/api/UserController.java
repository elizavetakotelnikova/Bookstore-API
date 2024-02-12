package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.infrastructure.HashingConfigure;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PBEKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.UUID;
@RestController
@AllArgsConstructor
public class UserController {
    private IUsersRepository usersRepository;
    private HashingConfigure hashingConfigure;
    @PostMapping("/users")
    public CreateResponseBody create(@RequestBody CreateUserViewModel providedUser) throws InvalidKeySpecException {
        var user = new User(providedUser.getPhoneNumber(), hashingConfigure.Hash(providedUser.getPassword()),
                 providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        usersRepository.save(user);
        return new CreateResponseBody(user.getId());
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable("userId") UUID userId) {
        return usersRepository.getUserById(userId);
    }

    @GetMapping("/user/{userId}")
    public String getUserToken(@RequestBody GetTokenViewModel providedData) {
        var user = usersRepository.geUserByPhoneNumber(providedData.phoneNumber);
        if (user.getPassword() != hashingConfigure.Hash(providedData.password)) {
            throw new SecurityException("Wrong password");
        }
        //тут нужно возвращать токен
    }
}
