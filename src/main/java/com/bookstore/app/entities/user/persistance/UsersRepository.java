package com.bookstore.app.entities.user.persistance;


import com.bookstore.app.entities.user.User;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class UsersRepository implements IUsersRepository {
    private Connection connection;

    @Override
    public User saveUser(User user) {
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
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return user;
    }

    @Override
    public User findUserById(UUID id) {
        try {
        PreparedStatement st = connection.prepareStatement(
                "SELECT id, phone_number, password, balance, birthday FROM users " +
                "WHERE id = ?");
        st.setObject(1, id);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) throw new QueryException("No such user");
        User user = new User(UUID.fromString(rs.getString("id")),
                rs.getString("phone_number"),
                rs.getBytes("password"),
                rs.getInt("balance"),
                rs.getObject("birthday", LocalDate.class));
        rs.close();
        st.close();
        return user;
    } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }
    }

    @Override
    public List<User> findUserByCriteria(FindCriteria criteria) {
        List<User> listOfUsers = new ArrayList<>();
        if (criteria.getPhoneNumber() != null) {
            var user = findUserByPhoneNumber(criteria.getPhoneNumber());
            if (user != null) listOfUsers.add(user);
        }
        if (criteria.getBirthday() != null) listOfUsers = findUserByBirthday(criteria.getBirthday());
        return listOfUsers;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, phone_number, password, balance, birthday FROM users " +
                            "WHERE phone_number = ?");
            st.setObject(1, phoneNumber);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such user");
            User user = new User(UUID.fromString(rs.getString("id")),
                    rs.getString("phone_number"),
                    rs.getBytes("password"),
                    rs.getInt("balance"),
                    rs.getObject("birthday", LocalDate.class));
            rs.close();
            st.close();
            return user;
        } catch (QueryException exception) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<User> findUserByBirthday(LocalDate date) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, phone_number, password, balance, birthday FROM users " +
                            "WHERE birthday = ?");
            st.setObject(1, date);
            ResultSet rs = st.executeQuery();
            List<User> listOfUsers = new ArrayList<>();
            while(rs.next()) {
                User user = new User(UUID.fromString(rs.getString("id")),
                        rs.getString("phone_number"),
                        rs.getBytes("password"),
                        rs.getInt("balance"),
                        rs.getObject("birthday", LocalDate.class));
                listOfUsers.add(user);
            }
            rs.close();
            st.close();
            return listOfUsers;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE users SET id = ?, phone_number = ?, password = ?, balance = ?, birthday = ?");
            st.setObject(1, user.getId());
            st.setString(2, user.getPhoneNumber());
            st.setBytes(3, user.getPassword());
            st.setInt(4, user.getBalance());
            st.setObject(5, user.getBirthday());
            st.execute();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    @Override
    public void deleteUserById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM users WHERE id = ?");
            st.setObject(1, id);
            st.execute();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
