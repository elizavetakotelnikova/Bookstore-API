package com.bookstore.app.entities.user;

import com.bookstore.application.entities.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String phoneNumber;
    private String password;
    private int balance;
    private Date birthday;
    private Order[] OrdersHistory;

    public User(String phoneNumber, String password, int balance, Date birthday, Order[] ordersHistory) {
        this.id = UUID.randomUUID();
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.birthday = birthday;
        this.OrdersHistory = ordersHistory;
    }
    public User(UUID id, String phoneNumber, String password, int balance, Date birthday) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.birthday = birthday;
    }
}

