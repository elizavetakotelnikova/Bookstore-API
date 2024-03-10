package com.bookstore.app.entities.productFeature.persistance;
import com.bookstore.app.exceptions.QueryException;
import com.bookstore.app.entities.productFeature.FeatureType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.UUID;
public class FeatureTypesRepository implements IFeatureTypesRepository {
    private static SessionFactory factory;
    private Connection connection;
    public FeatureTypesRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public FeatureTypesRepository(Connection connection) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
        this.connection = connection;
    }

    @Override
    public FeatureType saveFeatureType(FeatureType featureType) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(featureType);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return featureType;
    }

    @Override
    public FeatureType findFeatureTypeById(UUID id) {
        try (Session session = factory.openSession()) {
            var featureType = (FeatureType) session.get(FeatureType.class, id);
            if (featureType == null) throw new QueryException("No such featureType");
            return featureType;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FeatureType findFeatureByName(String name) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from FeatureType feature_type where feature_type.name = :name");
            query.setParameter("name", name);
            var featureTypes = query.list();
            if (featureTypes.isEmpty()) throw new QueryException("No such feature types");
            return (FeatureType) featureTypes;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FeatureType updateFeatureType(FeatureType featureType) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(featureType);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return featureType;
    }

    @Override
    public void deleteFeatureTypeById(UUID id) {
        try (Session session = factory.openSession()) {
            FeatureType featureType = (FeatureType)session.get(FeatureType.class, id);
            Transaction tx = session.beginTransaction();
            session.delete(featureType);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
