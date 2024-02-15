package com.bookstore.app.entities.user.api;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.api.viewModels.CreateUserViewModel;
import com.bookstore.app.infrastructure.HashingConfigure;

public class UserMapper {
    HashingConfigure hashingConfigure;
    public User MapUserViewModelToUser(CreateUserViewModel viewModel) {
        return new User(viewModel.getPhoneNumber(), hashingConfigure.Hash(viewModel.getPassword()),
                viewModel.getBalance(), viewModel.getBirthday(), viewModel.getOrdersHistory());
    }
}
