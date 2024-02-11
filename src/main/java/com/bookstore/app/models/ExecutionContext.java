package com.bookstore.app.models;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.user.User;
import lombok.Data;

import java.util.List;

@Data
public class ExecutionContext {
    private User currentUser;
    private List<Product> currentOrder;
}

