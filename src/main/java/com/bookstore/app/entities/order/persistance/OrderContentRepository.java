/*package com.bookstore.app.entities.order.persistance;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OrderContentRepository implements IOrderContetRepository {
    private Connection connection;
    @Override
    public void save(UUID order_id, UUID product_id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO order_content(order_id, product_id) VALUES(@order_id, @product_id)");
            st.setObject(1, order_id);
            st.setObject(2, product_id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); //вот тут будешь смотреть еще с returning id
        }
    }

    @Override
    public List<UUID> findAllProductsIdByOrderId(UUID id) { // переделать под продукты
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT product_id FROM order_content " +
                            "WHERE order_id == @id");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            List<Long> listOfIds = new ArrayList<>();
            while (rs.next())
            {
                listOfIds.add(rs.getLong("product_id"));
            }
            rs.close();
            st.close();
            return listOfIds;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteOrderContentByOrderId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM order_content WHERE order_id == @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}*/
