package com.bookstore.app.entities.user.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDetailsResponse {
    private UUID id;
    private String phoneNumber;
    private byte[] password;
    private int balance;
    private java.time.LocalDate birthday;
    private List<UUID> OrdersHistory;
}
