package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.Product;
import com.bookstore.app.exceptions.QueryException;
import com.bookstore.app.entities.productFeature.FeatureType;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.product.ProductType;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductsRepository implements IProductsRepository {
    private Connection connection;
    private static SessionFactory factory;
    public ProductsRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public ProductsRepository(Connection connection) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        this.connection = connection;
    }

    @Override
    public Product saveProduct(Product product) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(product);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return product;
    }

    @Override
    public Product findProductById(UUID id) {
        try (Session session = factory.openSession()) {
            var product = (Product) session.get(Product.class, id);
            if (product == null) throw new QueryException("No such product");
            return product;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Product product) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(product);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return product;
    }

    @Override
    public void deleteProductById(UUID id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Product product = (Product)session.get(Product.class, id);
            session.delete(product);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public List<Product> findProductsByCriteria(FindCriteria criteria) {
        if (criteria.typeId != null) return findProductsByTypeId(criteria.typeId);
        if (criteria.name != null) return findProductsByName(criteria.name);
        return null;
    }

    public List<Product> findProductsByTypeId(UUID id) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from Product product where product.type.id = :id");
            query.setParameter("id", id);
            var products = query.list();
            if (products == null) throw new QueryException("No such product");
            return products;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> findProductsByName(String name) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("SELECT product from Product product where product.name = :name");
            query.setParameter("name", name);
            var products = query.list();
            if (products == null) throw new QueryException("No such product");
            return products;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<Product> getProductContent(List<UUID> listOfIds) {
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
                if (!rs.next()) throw new QueryException("No such product");
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
