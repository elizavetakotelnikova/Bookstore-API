package com.bookstore.app.entities.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String phoneNumber;
    private byte[] password;
    private int balance;
    private java.time.LocalDate birthday;

    public User(String phoneNumber, byte[] password, int balance, java.time.LocalDate birthday) {
        this.id = UUID.randomUUID();
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.birthday = birthday;
    }
}

