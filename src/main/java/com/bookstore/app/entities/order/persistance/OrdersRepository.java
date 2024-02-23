package com.bookstore.app.entities.order.persistance;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.exceptions.QueryException;
import com.bookstore.app.models.FeatureType;
import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import com.bookstore.app.valueObjects.OrderState;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class OrdersRepository implements IOrdersRepository {
    private Connection connection;
    @Override
    public Order saveOrder(Order order) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO orders(id, user_id, date, shop_id, state) VALUES(?, ?, ?, ?, CAST(? AS order_state))");
            st.setObject(1, order.getId());
            st.setObject(2, order.getUserId());
            st.setDate(3, order.getDate());
            st.setObject(4, order.getShopId());
            st.setObject(5, order.getOrderState().toString().toLowerCase());
            st.execute();

            st = connection.prepareStatement(
                    "INSERT INTO order_content(order_id, product_id) VALUES(?, ?)");
            for (Product product : order.getProductList()) {
                st.setObject(1, order.getId());
                st.setObject(2, product.getId());
                st.execute();
            }
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return order;
    }

    @Override
    public Order findOrderById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, user_id, date, shop_id, state " +
                            "FROM orders WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such orders");
            Order order = new Order(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("user_id")),
                    rs.getDate("date"),
                    UUID.fromString(rs.getString("shop_id")),
                    OrderState.valueOf(rs.getString("state"))
                    );

            st = connection.prepareStatement(
                    "SELECT product_id FROM order_content " +
                            "WHERE order_id = ?");
            st.setObject(1, order.getId());
            List<UUID> listOfIds = new ArrayList<>();
            rs = st.executeQuery();
            while (!rs.next()) {
                listOfIds.add(UUID.fromString(rs.getString("product_id")));
            }
            order.setProductList(getOrderContent(listOfIds));
            rs.close();
            st.close();
            return order;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Order> findOrdersByCriteria(FindCriteria criteria) {
        if (criteria.getUserId() != null) return findAllOrdersByUserId(criteria.getUserId());
        if (criteria.getDate() != null) return findAllOrdersByDate(criteria.getDate());
        throw new RuntimeException("cannot find any orders");
    }

    public List<Order> findAllOrdersByUserId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id FROM orders " +
                            "WHERE user_id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such orders");
            List<Order> listOfOrders = new ArrayList<>();
            while (rs.next())
            {
                listOfOrders.add(findOrderById(UUID.fromString(rs.getString("id"))));
            }
            rs.close();
            st.close();
            return listOfOrders;
        } catch (QueryException e) {
            return new ArrayList<>();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Order> findAllOrdersByDate(LocalDate date) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id FROM orders "+
                    "WHERE date = ?");
            st.setDate(1, Date.valueOf(date));
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such orders");
            List<Order> listOfOrders = new ArrayList<>();
            while (rs.next())
            {
                listOfOrders.add(findOrderById(UUID.fromString(rs.getString("id"))));
            }
            rs.close();
            st.close();
            return listOfOrders;
        } catch (QueryException e) {
            return new ArrayList<>();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteOrderById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM orders WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            st = connection.prepareStatement(
                    "DELETE FROM order_content WHERE order_id = ?");
            st.setObject(1, id);
            rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Product> getOrderContent(List<UUID> listOfIds) {
        try {
            List<Product> productList = new ArrayList<>();
            for (var currentId : listOfIds) {
                PreparedStatement st = connection.prepareStatement(
                        "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                                "FROM products AS p " +
                                "INNER JOIN types AS t " +
                                "ON p.type_id == t.id " +
                                "WHERE id = ?");
                st.setObject(1, currentId);
                ResultSet rs = st.executeQuery();
                if (!rs.next()) throw new QueryException("No such order");
                Product product = new Product(
                        UUID.fromString(rs.getString("p.id")),
                        new ProductType(UUID.fromString(rs.getString("t.type_id")), rs.getString("t.name")),
                        rs.getString("p.name"),
                        rs.getInt("p.price")
                );

                st = connection.prepareStatement(
                        "SELECT f_v.id, f_v.feature_type_id, f_v.value, f_t.name " +
                                "FROM feature_value AS f_v " +
                                "INNER JOIN feature_type AS f_t " +
                                "ON f_v.feature_type_id == f_t.id " +
                                "WHERE f_v.id = ?");
                rs = st.executeQuery();
                while (!rs.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rs.getString("f_v.id")),
                            new FeatureType(
                                    UUID.fromString(rs.getString("f_v.feature_type_id")),
                                    rs.getString("f_t.name")),
                            rs.getString("f_v.value"));
                    product.getFeatures().add(currentFeature);
                }
                productList.add(product);
                rs.close();
                st.close();
            }
            return productList;
        } catch (QueryException e) {
            return new ArrayList<>();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE orders SET id = ?, user_id = ?, date = ?, shop_id = ?, state = CAST(? AS order_state)");
            st.setObject(1, order.getId());
            st.setObject(2, order.getUserId());
            st.setDate(3, order.getDate());
            st.setObject(4, order.getShopId());
            st.setObject(5, order.getOrderState().toString().toLowerCase());
            ResultSet rs = st.executeQuery();

            st = connection.prepareStatement(
                    "UPDATE order_content SET order_id = ?, product_id = ?");
            for (Product product : order.getProductList()) {
                st.setObject(1, order.getId());
                st.setObject(2, product.getId());
                rs = st.executeQuery();
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return order;
    }
}
