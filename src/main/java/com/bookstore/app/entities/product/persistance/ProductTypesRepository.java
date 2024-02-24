package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.entities.product.ProductType;
import com.bookstore.app.entities.product.persistance.IProductTypesRepository;
import com.bookstore.app.exceptions.QueryException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class ProductTypesRepository implements IProductTypesRepository {
    private Connection connection;
    @Override
    public ProductType saveProductType(ProductType productType) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO product_types(id, name) VALUES(?, ?)");
            st.setObject(1, productType.getId());
            st.setString(2, productType.getName());
            st.execute();
            st.close();
            return productType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductType findProductTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, name " +
                            "WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such product type");
            ProductType productType = new ProductType(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"));
            rs.close();
            st.close();
            return productType;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductType findProductTypeByName(String name) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, name " +
                            "WHERE name = ?");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new QueryException("No such product type");
            ProductType productType = new ProductType(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"));
            rs.close();
            st.close();
            return productType;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteProductTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM product_types WHERE id = ?");
            st.setObject(1, id);
            st.execute();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    @Modifying
    public ProductType updateProductType(ProductType productType) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE product_types SET name = ? " +
                            "WHERE id = type.id");
            st.setString(1, productType.getName());
            st.execute();
            st.close();
            return productType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
