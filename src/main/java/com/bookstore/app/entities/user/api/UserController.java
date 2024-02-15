package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.api.responses.TokenResponse;
import com.bookstore.app.entities.user.api.responses.UserIDResponse;
import com.bookstore.app.entities.user.api.viewModels.CreateUserViewModel;
import com.bookstore.app.entities.user.api.viewModels.GetTokenViewModel;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.*;
import com.bookstore.app.entities.user.usecases.commands.CreateUserCommand;
import com.bookstore.app.entities.user.usecases.commands.UpdateUserCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private GetTokenUsecase getTokenUsecase;
    @Autowired
    private CreateUserUsecase createUserUsecase;
    @Autowired
    private FindUserByIdUsecase findUserByIdUsecase;
    @Autowired
    private FindUsersByCriteriaUsecase findUsersByCriteriaUsecase;
    @Autowired
    private UpdateUserUsecase updateUserUsecase;
    @Autowired
    private DeleteUserUsecase deleteUserUsecase;
    //private UserMapper userMapper; not known where to place it

    @PostMapping("/users")
    public UserIDResponse createUser(@RequestBody CreateUserViewModel providedUser) throws InvalidKeySpecException {
        var command = new CreateUserCommand(providedUser.getPhoneNumber(), providedUser.getPassword(), providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        var user = createUserUsecase.CreateUser(command);
        return new UserIDResponse(user.getId());
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable("userId") UUID userId) {
        return findUserByIdUsecase.Handle(userId);
    }
    @GetMapping("/users/")
    public List<User> getUserByCriteria(@Param("birthday") String date, @Param("phoneNumber") String phoneNumber) {
        var criteria = new FindCriteria(null, null);
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
    public ResponseEntity<TokenResponse> getUserToken(@RequestBody GetTokenViewModel providedData) {
        var token = getTokenUsecase.ValidatePasswordFromDB(providedData.password, providedData.phoneNumber);
        if (token != null) return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK) ;
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/user/{userId}")
    public UserIDResponse updateUser(@PathVariable("userId") UUID id, @RequestBody CreateUserViewModel providedUser) {
        var command = new UpdateUserCommand(id, providedUser.getPhoneNumber(), providedUser.getPassword(),
                providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        var user = updateUserUsecase.Handle(command);
        return new UserIDResponse(user.getId());
    }

    @DeleteMapping("/user/{userId}")
    public void updateUser(@PathVariable("userId") UUID id) {
        deleteUserUsecase.Handle(id);
    }
}
