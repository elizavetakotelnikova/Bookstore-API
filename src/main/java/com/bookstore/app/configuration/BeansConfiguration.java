package com.bookstore.app.configuration;

import com.bookstore.app.entities.order.persistance.IOrdersRepository;
import com.bookstore.app.entities.order.persistance.OrdersRepository;
import com.bookstore.app.entities.product.persistance.FeatureTypesRepository;
import com.bookstore.app.entities.product.persistance.FeatureValuesRepository;
import com.bookstore.app.entities.product.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.product.persistance.IFeatureValuesRepository;
import com.bookstore.app.entities.user.api.UserMapper;
import com.bookstore.app.entities.user.persistance.IUsersRepository;
import com.bookstore.app.entities.user.persistance.UsersRepository;
import com.bookstore.app.infrastructure.HashingConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

@Configuration
public class BeansConfiguration {
    private DataSourceConnection source;
    private Connection connection;
    public BeansConfiguration() {
        source = new DataSourceConnection();
        connection = source.connect();
    }
    @Bean
    public IOrdersRepository IOrdersRepository() {
        return new OrdersRepository(connection);
    }
    @Bean
    public IUsersRepository IUsersRepository() {
        return new UsersRepository(connection);
    }
    @Bean
    public IFeatureValuesRepository IFeatureValuesRepository() {
        return new FeatureValuesRepository(connection);
    }
    @Bean
    public IFeatureTypesRepository IFeatureTypesRepository() {
        return new FeatureTypesRepository(connection);
    }
    @Bean
    public HashingConfigure HashingConfigure() throws NoSuchAlgorithmException {
        return new HashingConfigure();
    }
    @Bean
    public UserMapper UserMapper() {
        return new UserMapper();
    }
}
