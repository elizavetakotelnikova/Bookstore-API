package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.exceptions.QueryException;
import com.bookstore.app.entities.productFeature.FeatureType;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.product.ProductType;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ProductsRepository implements IProductsRepository {
    private Connection connection;
    @Override
    public Product saveProduct(Product product) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO products(id, type_id, price, name) VALUES(?, ?, ?, ?)");
            st.setObject(1, product.getId());
            st.setObject(2, product.getType().getId());
            st.setInt(3, product.getPrice());
            st.setString(4, product.getName());
            st.execute();

            st = connection.prepareStatement(
                    "INSERT INTO product_features(product_id, feature_value_id) VALUES(?, ?)");
            for (ProductFeature feature : product.getFeatures()) {
                st.setObject(1, product.getId());
                st.setObject(2, feature.getId());
                st.execute();
            }
            st.close();
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Product findProductById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id AS product_id, p.type_id AS type_id, p.price AS price, p.name AS product_name, t.name AS type_name " +
                            "FROM products AS p " +
                            "INNER JOIN product_types AS t " +
                            "ON p.type_id = t.id " +
                            "WHERE p.id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such product");
            Product product = new Product(
                    (UUID) rs.getObject("product_id"),
                    new ProductType((UUID) rs.getObject("type_id"), rs.getString("type_name")),
                    rs.getString("product_name"),
                    rs.getInt("price")
            );

            st = connection.prepareStatement(
                    "SELECT f_v.id AS value_id, f_v.feature_type_id AS feature_type_id, f_v.value AS value, f_t.name AS feature_type_name " +
                            "FROM feature_value AS f_v " +
                            "INNER JOIN feature_types AS f_t " +
                            "ON f_v.feature_type_id = f_t.id " +
                            "INNER JOIN product_features AS p_f " +
                            "ON f_v.id = p_f.feature_value_id " +
                            "WHERE p_f.product_id = ? ");
            st.setObject(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                ProductFeature currentFeature = new ProductFeature(UUID.fromString(rs.getString("value_id")),
                        new FeatureType((UUID) rs.getObject("feature_type_id"),
                        rs.getString("feature_type_name")),
                        rs.getString("value"));
                product.getFeatures().add(currentFeature);
            }
            rs.close();
            st.close();
            return product;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
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
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id AS product_id, p.type_id AS type_id, p.price AS price, p.name AS product_name, t.name AS type_name " +
                            "FROM products AS p " +
                            "INNER JOIN product_types AS t " +
                            "ON p.type_id = t.id " +
                            "WHERE p.type_id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            List<Product> listOfProducts = new ArrayList<>();
            while (rs.next())
            {
                var currentProduct = new Product(
                        (UUID) rs.getObject("product_id"),
                        new ProductType(UUID.fromString(rs.getString("type_id")), rs.getString("type_name")),
                        rs.getString("product_name"),
                        rs.getInt("price"));

                PreparedStatement stSecond = connection.prepareStatement(
                        "SELECT f_v.id AS value_id, f_v.feature_type_id AS feature_type_id, f_v.value AS value, f_t.name AS feature_type_name " +
                                "FROM feature_value AS f_v " +
                                "INNER JOIN feature_types AS f_t " +
                                "ON f_v.feature_type_id = f_t.id " +
                                "INNER JOIN product_features AS p_f " +
                                "ON f_v.id = p_f.feature_value_id " +
                                "WHERE p_f.product_id = ? ");
                stSecond.setObject(1, currentProduct.getId());
                ResultSet rsSecond = stSecond.executeQuery();
                while (rsSecond.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rsSecond.getString("value_id")),
                            new FeatureType(
                                    (UUID) rsSecond.getObject("feature_type_id"),
                                    rsSecond.getString("feature_type_name")),
                            rsSecond.getString("value"));
                    currentProduct.getFeatures().add(currentFeature);
                }
                listOfProducts.add(currentProduct);
                rsSecond.close();
            }
            rs.close();
            st.close();
            return listOfProducts;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> findProductsByName(String name) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id AS product_id, p.type_id AS type_id, p.price AS price, p.name AS product_name, t.name AS type_name " +
                            "FROM products AS p " +
                            "INNER JOIN product_types AS t " +
                            "ON p.type_id = t.id " +
                            "WHERE p.name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            List<Product> listOfProducts = new ArrayList<>();
            while (rs.next())
            {
                var currentProduct = new Product(
                        (UUID) rs.getObject("product_id"),
                        new ProductType((UUID) rs.getObject("type_id"), rs.getString("type_name")),
                        rs.getString("product_name"),
                        rs.getInt("price"));

                PreparedStatement stSecond = connection.prepareStatement(
                        "SELECT f_v.id AS value_id, f_v.feature_type_id AS feature_type_id, f_v.value AS feature_value, f_t.name AS feature_type_name " +
                                "FROM feature_value AS f_v " +
                                "INNER JOIN feature_types AS f_t " +
                                "ON f_v.feature_type_id = f_t.id " +
                                "INNER JOIN product_features AS p_f " +
                                "ON f_v.id = p_f.feature_value_id " +
                                "WHERE p_f.product_id = ? ");
                stSecond.setObject(1, currentProduct.getId());
                ResultSet rsSecond = stSecond.executeQuery();
                while (rsSecond.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rsSecond.getString("value_id")),
                            new FeatureType(
                                    (UUID) rsSecond.getObject("feature_type_id"),
                                    rsSecond.getString("feature_type_name")),
                            rsSecond.getString("feature_value"));
                    currentProduct.getFeatures().add(currentFeature);
                }
                listOfProducts.add(currentProduct);
                rsSecond.close();
            }
            rs.close();
            st.close();
            return listOfProducts;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Product product) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE products SET id = ?, type_id = ?, price = ?, name = ?");
            st.setObject(1, product.getId());
            st.setObject(2, product.getType().getId());
            st.setInt(3, product.getPrice());
            st.setString(4, product.getName());
            st.execute();

            st = connection.prepareStatement(
                    "UPDATE product_features SET product_id = ?, feature_value_id = ?");

            for (ProductFeature feature : product.getFeatures()) {
                st.setObject(1, product.getId());
                st.setObject(2, feature.getId());
                st.execute();
            }
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return product;
    }



    @Override
    public void deleteProductById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM product_features WHERE product_id = ?");
            st.setObject(1, id);
            st.execute();
            st.close();

            st = connection.prepareStatement(
                    "DELETE FROM products WHERE id = ?");
            st.setObject(1, id);
            st.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
