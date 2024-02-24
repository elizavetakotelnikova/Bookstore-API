package com.bookstore.app.entities.user.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUserDTO {
    private String phoneNumber;
    private String password;
    private int balance;
    private java.time.LocalDate birthday;
    private List<UUID> ordersHistory;

}
