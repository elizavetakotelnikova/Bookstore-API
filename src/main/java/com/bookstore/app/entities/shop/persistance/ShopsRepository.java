package com.bookstore.app.entities.shop.persistance;
import com.bookstore.app.entities.order.Order;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.productFeature.FeatureType;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.exceptions.QueryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class ShopsRepository implements IShopsRepository {
    private static SessionFactory factory;
    private Connection connection;
    public ShopsRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public ShopsRepository(Connection connection) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        this.connection = connection;
    }

    @Override
    public Shop saveShop(Shop shop) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(shop);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return shop;
    }

    @Override
    public Shop findShopById(UUID id) {
        try (Session session = factory.openSession()) {
            var shop = (Shop) session.get(Shop.class, id);
            if (shop == null) throw new QueryException("No such shop");
            return shop;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<Shop> findShopsByCity(String city) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Shop shop where shop.address.city = :city");
            query.setParameter("city", city);
            var shops = query.list();
            if (shops == null) throw new QueryException("No such shops");
            return shops;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Shop> findShopsByCriteria(FindCriteria criteria) {
        List<Shop> shops = new ArrayList<>();
        if (criteria.city != null) shops = findShopsByCity(criteria.city);
        return shops;
    }

    @Override
    public Shop updateShop(Shop shop) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(shop);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return shop;
    }

    @Override
    public void deleteShopById(UUID id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Shop shop = (Shop)session.get(Shop.class, id);
            session.delete(shop);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Product> getShopContent(List<UUID> listOfIds) {
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
                if (!rs.next()) throw new QueryException("No such shop");
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
