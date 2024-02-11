package com.bookstore.app.entities.product.persistance;

import com.bookstore.app.models.FeatureType;
import com.bookstore.app.models.ProductFeature;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class FeatureValuesRepository implements IFeatureValuesRepository {
    private Connection connection;

    @Override
    public ProductFeature save(ProductFeature productFeature) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO feature_value(id, feature_type_id, value) VALUES(@feature_type_id, @value)");
            st.setObject(1, productFeature.getType().getId());
            st.setString(2, productFeature.getValue());
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            rs.close();
            st.close();
            return productFeature;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductFeature getProductFeatureByFeatureId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT f_v.id, f_v.feature_type_id, f_v.value, f_t.name " +
                            "FROM feature_value AS f_v" +
                            "INNER JOIN feature_type AS f_t" +
                            "ON f_v.feature_type_id == f_t.id" +
                            "WHERE f_v.id = @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            ProductFeature productFeature= new ProductFeature(
                        UUID.fromString(rs.getString("f_v.id")),
                        new FeatureType(UUID.fromString(rs.getString("f_v.feature_type_id")), rs.getString("f_t.name")),
                        rs.getString("f_v.value"));
            rs.close();
            st.close();
            return productFeature;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductFeature> findAllFeaturesValuesByFeatureTypeId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT f_v.id, f_v.feature_type_id, f_v.value, f_t.name " +
                            "FROM feature_value AS f_v" +
                            "INNER JOIN feature_type AS f_t" +
                            "ON f_v.feature_type_id == f_t.id" +
                            "WHERE f_v.feature_type_id = @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
            List<ProductFeature> listOfFeatures = new ArrayList<>();
            while (rs.next())
            {
                listOfFeatures.add(new ProductFeature(
                        UUID.fromString(rs.getString("f_v.id")),
                        new FeatureType(UUID.fromString(rs.getString("f_v.feature_type_id")),
                                rs.getString("f_t.name")),
                        rs.getString("f_v.value")));
            }
            rs.close();
            st.close();
            return listOfFeatures;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteFeatureByFeatureId(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM feature_value WHERE id == @id");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            rs.close();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
