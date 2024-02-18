package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.api.responses.TokenResponse;
import com.bookstore.app.entities.user.api.responses.UserIDResponse;
import com.bookstore.app.entities.user.api.responses.UserResponse;
import com.bookstore.app.entities.user.api.viewModels.CreateUserViewModel;
import com.bookstore.app.entities.user.api.viewModels.GetTokenViewModel;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.usecases.*;
import com.bookstore.app.entities.user.usecases.DTOs.UserDetailsDTO;
import com.bookstore.app.entities.user.usecases.commands.CreateUserCommand;
import com.bookstore.app.entities.user.usecases.commands.UpdateUserCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private GetTokenUseCase getTokenUsecase;
    @Autowired
    private CreateUserUseCase createUserUsecase;
    @Autowired
    private FindUserByIdUseCase findUserByIdUsecase;
    @Autowired
    private FindUsersByCriteriaUseCase findUsersByCriteriaUsecase;
    @Autowired
    private UpdateUserUseCase updateUserUsecase;
    @Autowired
    private DeleteUserUseCase deleteUserUsecase;

    @PostMapping("/users")
    public UserIDResponse createUser(@RequestBody CreateUserViewModel providedUser) throws InvalidKeySpecException {
        var command = new CreateUserCommand(providedUser.getPhoneNumber(), providedUser.getPassword(), providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        UserDetailsDTO user;
        try {
            user = createUserUsecase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new UserIDResponse(user.getId());
    }

    @GetMapping("/user/{userId}")
    public UserResponse getUserById(@PathVariable("userId") UUID userId) {
        var user = findUserByIdUsecase.handle(userId);
        return new UserResponse(user.getId(), user.getPhoneNumber(),
                user.getPassword(), user.getBalance(), user.getBirthday(),
                user.getOrdersHistory());
    }
    @GetMapping("/users/")
    public List<UserResponse> getUserByCriteria(@Param("birthday") String date, @Param("phoneNumber") String phoneNumber) {
        var criteria = new FindCriteria(null, null);
        try {
           var newDate = LocalDate.parse(date);
        }
        catch (Exception e) {
            criteria.setBirthday(null);
        }
        if (!phoneNumber.isEmpty()) criteria.setPhoneNumber(phoneNumber);
        var users = findUsersByCriteriaUsecase.handle(criteria);
        var userResponses = new ArrayList<UserResponse>();
        for (UserDetailsDTO each : users) {
            userResponses.add(new UserResponse(each.getId(),
                    each.getPhoneNumber(), each.getPassword(), each.getBalance(),
                    each.getBirthday(), each.getOrdersHistory()));
        }
        return userResponses;
    }

    @GetMapping("/user/token/{userId}")
    public ResponseEntity<TokenResponse> getUserToken(@RequestBody GetTokenViewModel providedData) {
        var token = getTokenUsecase.validatePasswordFromDB(providedData.password, providedData.phoneNumber);
        if (token != null) return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK) ;
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/user/{userId}")
    public UserIDResponse updateUser(@PathVariable("userId") UUID id, @RequestBody CreateUserViewModel providedUser) {
        var command = new UpdateUserCommand(id, providedUser.getPhoneNumber(), providedUser.getPassword(),
                providedUser.getBalance(), providedUser.getBirthday(), providedUser.getOrdersHistory());
        UserDetailsDTO user = null;
        try {
            user = updateUserUsecase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new UserIDResponse(user.getId());
    }

    @DeleteMapping("/user/{userId}")
    public void updateUser(@PathVariable("userId") UUID id) {
        deleteUserUsecase.handle(id);
    }
}
