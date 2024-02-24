package com.bookstore.app.entities.productFeature.persistance;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.models.FeatureType;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@AllArgsConstructor
public class FeatureTypesRepository implements IFeatureTypesRepository {
    private Connection connection;
    @Override
    public FeatureType saveFeatureType(FeatureType featureType) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO feature_types(id, name) VALUES(?, ?)");
            st.setObject(1, featureType.getId());
            st.setString(2, featureType.getName());
            st.execute();
            st.close();
            return featureType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FeatureType findFeatureTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT id, name FROM feature_types " +
                            "WHERE id = ?");
            st.setObject(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) throw new SQLException();
           FeatureType featureType = new FeatureType(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("name"));
            rs.close();
            st.close();
            return featureType;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteFeatureTypeById(UUID id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM feature_types WHERE id == ?");
            st.setObject(1, id);
            st.executeQuery();
            st.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
