package com.bookstore.app.entities.order.persistance;

import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.exceptions.QueryException;
import com.bookstore.app.entities.productFeature.FeatureType;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.product.ProductType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdersRepository implements IOrdersRepository {
    private static SessionFactory factory;
    private Connection connection;
    public OrdersRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public OrdersRepository(Connection connection) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        this.connection = connection;
    }

    @Override
    public Order saveOrder(Order order) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return order;
    }

    @Override
    public Order findOrderById(UUID id) {
        try (Session session = factory.openSession()) {
            var order = (Order) session.get(Order.class, id);
            if (order == null) throw new QueryException("No such order");
            return order;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(order);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return order;
    }

    @Override
    public void deleteOrderById(UUID id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Order order = (Order)session.get(Order.class, id);
            session.delete(order);
            tx.commit();
        } catch (Exception e) {
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
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Order order where order.userId = :user_id");
            query.setParameter("user_id", id);
            var orders = query.list();
            if (orders == null) throw new QueryException("No such order");
            return orders;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Order> findAllOrdersByDate(LocalDate date) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("SELECT order from Order order where order.date = :date");
            query.setParameter("date", date);
            var orders = query.list();
            if (orders == null) throw new QueryException("No such order");
            return orders;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
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
}
