package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.ProductType;
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
    public ProductType save(ProductType productType) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO product_types(name) VALUES(@name) " +
                            "RETURNING id");
            st.setString(1,productType.getName());
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            productType.setId(UUID.fromString(rs.getString("id")));
            rs.close();
            st.close();
            return productType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductType getProductTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, name " +
                            "WHERE id = @id");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            ProductType productType = new ProductType(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"));
            rs.close();
            st.close();
            return productType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteProductTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM product_types WHERE id == @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    @Modifying
    public ProductType updateProductTypeNameById(UUID id, String newName) {
        ProductType productType = this.getProductTypeById(id);
        productType.setName(newName);
        return productType;
    }
}
