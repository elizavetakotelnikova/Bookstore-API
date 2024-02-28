package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteFeatureTypeUseCase {
    private IFeatureTypesRepository featureTypesRepository;
    public void handle(UUID id) throws QueryException {
        if (featureTypesRepository.findFeatureTypeById(id) != null) {
            throw new QueryException("No such feature type");
        }
        featureTypesRepository.deleteFeatureTypeById(id);
    }
}
