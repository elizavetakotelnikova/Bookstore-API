package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.models.FeatureType;
import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    "INSERT INTO products(id, type_id, price) VALUES(?, ?, ?)");
            st.setObject(1, product.getId());
            st.setObject(2, product.getType().getId());
            st.setInt(3, product.getPrice());
            st.executeQuery();

            st = connection.prepareStatement(
                    "INSERT INTO product_features(product_id, feature_id) VALUES(?, ?)");
            for (ProductFeature feature : product.getFeatures()) {
                st.setObject(1, product.getId());
                st.setObject(2, feature.getId());
                st.executeQuery();
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
                    "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                            "FROM products AS p " +
                            "INNER JOIN types AS t " +
                            "ON p.type_id = t.id " +
                            "WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
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
                            "WHERE f_v.id = ? ");
            rs = st.executeQuery();
            while (!rs.next()) {
                ProductFeature currentFeature = new ProductFeature(UUID.fromString(rs.getString("f_v.id")),
                        new FeatureType(
                        UUID.fromString(rs.getString("f_v.feature_type_id")),
                        rs.getString("f_t.name")),
                        rs.getString("f_v.value"));
                product.getFeatures().add(currentFeature);
            }
            rs.close();
            st.close();
            return product;
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
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                            "FROM products AS p " +
                            "WHERE p.type_id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            List<Product> listOfProducts = new ArrayList<>();
            while (rs.next())
            {
                var currentProduct = new Product(
                        UUID.fromString(rs.getString("p.id")),
                        new ProductType(UUID.fromString(rs.getString("t.type_id")), rs.getString("t.name")),
                        rs.getString("p.name"),
                        rs.getInt("p.price"));

                PreparedStatement stSecond = connection.prepareStatement(
                        "SELECT f_v.id, f_v.feature_type_id, f_v.value, f_t.name " +
                                "FROM feature_value AS f_v " +
                                "INNER JOIN feature_type AS f_t " +
                                "ON f_v.feature_type_id = f_t.id " +
                                "WHERE f_v.feature_type_id = ?");
                stSecond.setObject(1, id);
                ResultSet rsSecond = stSecond.executeQuery();
                while (rsSecond.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rsSecond.getString("f_v.id")),
                            new FeatureType(
                                    UUID.fromString(rsSecond.getString("f_v.feature_type_id")),
                                    rsSecond.getString("f_t.name")),
                            rsSecond.getString("f_v.value"));
                    currentProduct.getFeatures().add(currentFeature);
                    rs = st.executeQuery();
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
                    "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                            "FROM products AS p " +
                            "INNER JOIN types AS t " +
                            "ON p.type_id = t.id " +
                            "WHERE p.name = ?");
            st.setObject(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            List<Product> listOfProducts = new ArrayList<>();
            while (rs.next())
            {
                var currentProduct = new Product(
                        UUID.fromString(rs.getString("p.id")),
                        new ProductType(UUID.fromString(rs.getString("t.type_id")), rs.getString("t.name")),
                        rs.getString("p.name"),
                        rs.getInt("p.price"));

                PreparedStatement stSecond = connection.prepareStatement(
                        "SELECT f_v.id, f_v.feature_type_id, f_v.value, f_t.name " +
                                "FROM feature_value AS f_v " +
                                "INNER JOIN feature_type AS f_t " +
                                "ON f_v.feature_type_id = f_t.id " +
                                "WHERE f_v.feature_type_id = ?");
                stSecond.setObject(1, currentProduct.getType());
                ResultSet rsSecond = stSecond.executeQuery();
                while (rsSecond.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rsSecond.getString("f_v.id")),
                            new FeatureType(
                                    UUID.fromString(rsSecond.getString("f_v.feature_type_id")),
                                    rsSecond.getString("f_t.name")),
                            rsSecond.getString("f_v.value"));
                    currentProduct.getFeatures().add(currentFeature);
                    rs = st.executeQuery();
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
                    "UPDATE products SET id = ?, type_id = ?, price = ? VALUES(?, ?, ?)");
            st.setObject(1, product.getId());
            st.setObject(2, product.getType().getId());
            st.setInt(3, product.getPrice());
            st.executeQuery();

            st = connection.prepareStatement(
                    "INSERT INTO product_features SET product_id = ?, feature_id = ? VALUES(?, ?)");
            for (ProductFeature feature : product.getFeatures()) {
                st.setObject(1, product.getId());
                st.setObject(2, feature.getId());
                st.executeQuery();
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
                    "DELETE FROM products WHERE id = ?");
            st.setObject(1, id);
            st.executeQuery();

            st = connection.prepareStatement(
                    "DELETE FROM product_features WHERE product_id = ?");
            st.setObject(1, id);
            st.executeQuery();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
