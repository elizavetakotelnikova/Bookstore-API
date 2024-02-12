package com.bookstore.app.entities.user.persistance;


import com.bookstore.app.entities.user.User;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class UsersRepository implements IUsersRepository {
    private Connection connection;

    @Override
    public User save(User user) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO users(id, phone_number, password, balance, birthday) VALUES(?, ?, ?, ?, ?)");
            st.setObject(1, user.getId());
            st.setString(2, user.getPhoneNumber());
            st.setBytes(3, user.getPassword());
            st.setInt(4, user.getBalance());
            st.setObject(5, user.getBirthday());
            st.execute();
            st.close();
            //check orders?
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return user;
    }

    @Override
    public User getUserById(UUID id) {
        try {
        PreparedStatement st = connection.prepareStatement(
                "SELECT id, phone_number, password, balance, birthday FROM users " +
                "WHERE id = ?");
        st.setObject(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) throw new SQLException();
        User user = new User(UUID.fromString(rs.getString("id")),
                rs.getString("phone_number"),
                rs.getBytes("password"),
                rs.getInt("balance"),
                rs.getObject("birthday", LocalDate.class));
        st = connection.prepareStatement(
                    "SELECT id FROM orders" +
                            "WHERE user_id = ?");
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
    public User geUserByPhoneNumber(String phoneNumber) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, phone_number, password, balance, birthday FROM users " +
                            "WHERE phone_number = ?");
            st.setObject(1, phoneNumber);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            User user = new User(UUID.fromString(rs.getString("id")),
                    rs.getString("phone_number"),
                    rs.getBytes("password"),
                    rs.getInt("balance"),
                    rs.getObject("birthday", LocalDate.class));
            st = connection.prepareStatement(
                    "SELECT id FROM orders" +
                            "WHERE user_id = ?");
            st.setObject(1, user.getId());
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
    public void updateUser() {

    }  //подумать над этим

    @Override
    public void deleteUserById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
