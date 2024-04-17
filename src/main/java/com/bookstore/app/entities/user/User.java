package com.bookstore.app.entities.user;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID id;
    @Column(name = "phone_number")
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

