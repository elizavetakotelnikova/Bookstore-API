package com.bookstore.app.entities.shop.persistance;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.usecases.CreateShopUseCase;
import com.bookstore.app.models.ShopAddress;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ShopsRepository implements IShopsRepository {
    private Connection connection;
    @Override
    public Shop saveShop(Shop shop) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO shops(id, country, city, street, house_number, building_number) VALUES(?, ?, ?, ?, ?, ?)");
            st.setObject(1, shop.getId());
            st.setString(2, shop.getAddress().getCountry());
            st.setString(3, shop.getAddress().getCity());
            st.setString(4, shop.getAddress().getStreet());
            st.setString(5, shop.getAddress().getHouseNumber());
            st.setInt(6, shop.getAddress().getBuildingNumber());
            st.execute();
            st.close();
            return shop;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Shop findShopById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, country, city, street, house_number, building_number " +
                            "FROM shops " +
                            "WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            Shop shop = new Shop(
                    UUID.fromString(rs.getString("id")),
                    new ShopAddress(
                            rs.getString("country"),
                            rs.getString("city"),
                            rs.getString("street"),
                            rs.getString("house_number"),
                            rs.getInt("building_number")));
            rs.close();
            st.close();
            return shop;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Shop> findShopsByCriteria(FindCriteria criteria) {
        if (criteria.city != null) return findAllShopsByCity(criteria.city);
        return null;
    }

    public List<Shop> findAllShopsByCity(String city) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, country, city, street, house_number, building_number " +
                            "FROM shops " +
                            "WHERE city = ?");
            st.setString(1, city);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            List<Shop> listOfShops = new ArrayList<>();
            while (rs.next())
            {
                listOfShops.add(new Shop(
                        UUID.fromString(rs.getString("id")),
                        new ShopAddress(
                                rs.getString("country"),
                                rs.getString("city"),
                                rs.getString("street"),
                                rs.getString("house_number"),
                                rs.getInt("building_number"))));
            }
            rs.close();
            st.close();
            return listOfShops;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteShopById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM shops WHERE id == ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Shop updateShop(Shop shop) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE shops SET id = ?, country = ?, city = ?, street = ?, house_number = ?, building_number = ?");
            st.setObject(1, shop.getId());
            st.setString(2, shop.getAddress().getCountry());
            st.setString(3, shop.getAddress().getCity());
            st.setString(4, shop.getAddress().getStreet());
            st.setString(5, shop.getAddress().getHouseNumber());
            st.setInt(6, shop.getAddress().getBuildingNumber());
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
            return shop;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
