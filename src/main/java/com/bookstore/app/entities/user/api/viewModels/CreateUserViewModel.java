package com.bookstore.app.entities.user.api.viewModels;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUserViewModel {
    private String phoneNumber;
    private String password;
    private int balance;
    private java.time.LocalDate birthday;
    private List<UUID> ordersHistory;

}
