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
    public Product save(Product product) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO products(id, type_id, price) VALUES(@product_id, @type_id, @price)");
            st.setObject(1, product.getId());
            st.setObject(2, product.getType().getId());
            st.setInt(3, product.getPrice());
            ResultSet rs = st.executeQuery();

            st = connection.prepareStatement(
                    "INSERT INTO product_features(product_id, feature_id) VALUES(@product_id, @feature_id)");
            for (ProductFeature feature : product.getFeatures()) {
                st.setObject(1, product.getId());
                st.setObject(2, feature.getId());
                rs = st.executeQuery();
            }
            rs.close();
            st.close();
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Product getProductById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                            "FROM products AS p" +
                            "INNER JOIN types AS t" +
                            "ON p.type_id == t.id" +
                            "WHERE id = @product_id");
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
                            "FROM feature_value AS f_v" +
                            "INNER JOIN feature_type AS f_t" +
                            "ON f_v.feature_type_id == f_t.id" +
                            "WHERE f_v.id = @id");
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
    public List<Product> findAllProductsByTypeId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT p.id, p.type_id, p.price, p.name, t.name " +
                            "FROM products AS p" +
                            "INNER JOIN types AS t" +
                            "ON p.type_id == t.id" +
                            "WHERE p.type_id = @type_id");
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

                while (!rs.next()) {
                    ProductFeature currentFeature = new ProductFeature(UUID.fromString(rs.getString("f_v.id")),
                            new FeatureType(
                                    UUID.fromString(rs.getString("f_v.feature_type_id")),
                                    rs.getString("f_t.name")),
                            rs.getString("f_v.value"));
                    currentProduct.getFeatures().add(currentFeature);
                    rs = st.executeQuery();
                }
                listOfProducts.add(currentProduct);
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
        deleteProductById(product.getId());
        save(product);
        return product;
    }



    @Override
    public void deleteProductById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM products WHERE id == @product_id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();

            st = connection.prepareStatement(
                    "DELETE FROM product_features WHERE product_id == @id");
            st.setObject(1, id);
            rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
