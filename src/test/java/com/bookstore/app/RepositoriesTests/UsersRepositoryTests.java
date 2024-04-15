package com.bookstore.app.RepositoriesTests;

import com.bookstore.app.entities.user.User;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.persistance.UsersRepository;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.flywaydb.core.Flyway;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionCreator;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsersRepositoryTests {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    IUsersRepository usersRepostirory;
    User testUser;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection connectionProvider = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        var flyaway = setupFlyway(postgres);
        flyaway.clean();
        flyaway.migrate();
        usersRepostirory = new UsersRepository();
    }
    private Flyway setupFlyway(PostgreSQLContainer container) {
        return new Flyway(
                Flyway.configure()
                        .locations("/db.migration")
                        .dataSource(container.getJdbcUrl(), container.getUsername(),
                                container.getPassword())
        );
    }
    @BeforeEach
    void setUpUser() {
        byte[] testPassword;
        try {
            testPassword = new HashingConfigure().Hash("RTY32120c");
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
        testUser = new User("8981678788554", testPassword,
                0, LocalDate.parse("2004-02-01"));
    }

    @Test
    void testSavingUser() {
        usersRepostirory.saveUser(testUser);
        var foundUser = usersRepostirory.findUserById(testUser.getId());
        assert (foundUser.equals(testUser));
    }
    @Test
    void testFindingUsersByCriteria() {
        usersRepostirory.saveUser(testUser);
        var secondUser = new User("89816998935", testUser.getPassword(),
                testUser.getBalance(), testUser.getBirthday());
        usersRepostirory.saveUser(secondUser);
        var anotherBirthday = LocalDate.parse("2004-03-12");
        var thirdUser = new User("89816999945", testUser.getPassword(),
                testUser.getBalance(), anotherBirthday);
        usersRepostirory.saveUser(thirdUser);
        var users = usersRepostirory.findUserByCriteria(new FindCriteria(null, testUser.getBirthday()));
        assert(users.size() == 2);
        assert(users.contains(testUser));
        assert(users.contains(secondUser));
        assert(!users.contains(thirdUser));
    }
    @Test
    void testFindingUsersByPhoneNumber() {
        usersRepostirory.saveUser(testUser);
        var secondUser = new User("89816998935", testUser.getPassword(),
                testUser.getBalance(), testUser.getBirthday());
        usersRepostirory.saveUser(secondUser);
        var anotherBirthday = LocalDate.parse("2004-03-12");
        var thirdUser = new User("89816999945", testUser.getPassword(),
                testUser.getBalance(), anotherBirthday);
        usersRepostirory.saveUser(thirdUser);
        var users = usersRepostirory.findUserByCriteria(new FindCriteria("89816999945", null));
        assert(users.contains(thirdUser));
    }
    @Test
    void testUpdateUser() {
        usersRepostirory.saveUser(testUser);
        testUser.setBalance(1000);
        usersRepostirory.updateUser(testUser);
        var updatedUser = usersRepostirory.findUserById(testUser.getId());
        assert(updatedUser.equals(testUser));
    }
    @Test
    void testDeleteUser() {
        usersRepostirory.saveUser(testUser);
        usersRepostirory.deleteUserById(testUser.getId());
        var foundUser = usersRepostirory.findUserById(testUser.getId());
        assert(foundUser == null);
    }
}
