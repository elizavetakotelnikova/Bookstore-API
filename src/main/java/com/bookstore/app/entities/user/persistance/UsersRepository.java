package com.bookstore.app.entities.user.persistance;


import com.bookstore.app.entities.user.User;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class UsersRepository implements IUsersRepository {
    private Connection connection;

    @Override
    public User save(User user) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO users(id, login, password, balance, birthday) VALUES(@id, @login, @password, @balance, @birthday)");
            st.setObject(1, user.getId());
            st.setString(2, user.getPhoneNumber());
            st.setString(3, user.getPassword());
            st.setInt(4, user.getBalance());
            st.setDate(5, (Date) user.getBirthday());
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return user;
    }

    @Override
    public User getUserById(UUID id) {
        try {

        PreparedStatement st = connection.prepareStatement(
                "SELECT id, login, password, balance, birthday FROM users " +
                "WHERE id = @id");
        st.setObject(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) throw new SQLException();
        User user = new User(UUID.fromString(rs.getString("id")),
                rs.getString("login"),
                rs.getString("password"),
                rs.getInt("balance"),
                rs.getDate("birthday"));
        st = connection.prepareStatement(
                    "SELECT id FROM orders" +
                            "WHERE user_id = @id");
        st.setObject(1, id);
        while (rs.next()) {
            user.getOrdersHistory().add(UUID.fromString(rs.getString("id")));
        }
        rs.close();
        st.close();
        return user;
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
    }

    @Override
    public String getPasswordById(UUID id) {
        try {

            PreparedStatement st = connection.prepareStatement(
                    "SELECT password FROM users" +
                            "WHERE id = @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            String password = rs.getString("password");
            rs.close();
            st.close();
            return password;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUser() {

    }  //подумать над этим

    @Override
    public void deleteUserById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM users WHERE id = @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
