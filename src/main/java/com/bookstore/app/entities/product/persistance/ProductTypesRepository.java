package com.bookstore.app.entities.product.persistance;
import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.Connection;
import java.util.UUID;

public class ProductTypesRepository implements IProductTypesRepository {
    private static SessionFactory factory;
    private Connection connection;
    public ProductTypesRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public ProductTypesRepository(Connection connection) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        this.connection = connection;
    }

    @Override
    public ProductType saveProductType(ProductType productType) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(productType);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return productType;
    }

    @Override
    public ProductType findProductTypeById(UUID id) {
        try (Session session = factory.openSession()) {
            var productType = (ProductType) session.get(ProductType.class, id);
            if (productType == null) throw new QueryException("No such product type");
            return productType;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductType updateProductType(ProductType productType) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(productType);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return productType;
    }

    @Override
    public void deleteProductTypeById(UUID id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            ProductType productType = (ProductType)session.get(ProductType.class, id);
            session.delete(productType);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public ProductType findProductTypeByName(String name) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from ProductType productType where productType.name = :name");
            query.setParameter("name", name);
            var productTypes = query.list();
            if (productTypes.isEmpty()) throw new QueryException("No such productType");
            return (ProductType) productTypes;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
