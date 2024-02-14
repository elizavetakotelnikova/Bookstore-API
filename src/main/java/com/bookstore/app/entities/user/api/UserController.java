package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.infrastructure.HashingConfigure;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@RestController
@AllArgsConstructor
public class UserController {
    private IUsersRepository usersRepository;
    private HashingConfigure hashingConfigure;
    //private UserMapper userMapper; not known where to place it

    @PostMapping("/users")
    public UserIDResponse createUser(@RequestBody CreateUserViewModel providedUser) throws InvalidKeySpecException {
        var userMapper = new UserMapper();
        var user = userMapper.MapUserViewModelToUser(providedUser);
        usersRepository.save(user);
        return new UserIDResponse(user.getId());
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable("userId") UUID userId) {
        return usersRepository.findUserById(userId);
    }
    @GetMapping("/users/")
    public List<User> getUserByCriteria(@Param("birthday") String date, @Param("phoneNumber") String phoneNumber) {
        var criteria = new FindCriteria();
        try {
           var newDate = LocalDate.parse(date);
        }
        catch (Exception e) {
            criteria.setBirthday(null);
        }
        if (!phoneNumber.isEmpty()) criteria.setPhoneNumber(phoneNumber);

        return usersRepository.findUserByCriteria(criteria);
    }

    @GetMapping("/user/{userId}")
    public String getUserToken(@RequestBody GetTokenViewModel providedData) {
        var user = usersRepository.findUserByPhoneNumber(providedData.phoneNumber);
        if (user.getPassword() != hashingConfigure.Hash(providedData.password)) {
            throw new SecurityException("Wrong password");
        }
        //тут нужно возвращать токен
        return null;
    }

    @PutMapping("/user/{userId}")
    public UserIDResponse updateUser(@PathVariable("userId") UUID id, @RequestBody CreateUserViewModel providedUser) {
        var userMapper = new UserMapper();
        var user = userMapper.MapUserViewModelToUser(providedUser);
        user = usersRepository.updateUser(user);
        return new UserIDResponse(user.getId());
    }

    @DeleteMapping("/user/{userId}")
    public void updateUser(@PathVariable("userId") UUID id) {
        usersRepository.deleteUserById(id);
    }
}
