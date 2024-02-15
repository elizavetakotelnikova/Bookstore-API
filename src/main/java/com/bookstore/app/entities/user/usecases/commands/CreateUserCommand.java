package com.bookstore.app.entities.user.usecases.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateUserCommand {
    private String phoneNumber;
    private String password;
    private int balance;
    private java.time.LocalDate birthday;
    private List<UUID> OrdersHistory;
}
