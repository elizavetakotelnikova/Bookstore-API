package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.productFeature.FeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindFeatureTypeByIdUseCase {
    @Autowired
    private IFeatureTypesRepository featureTypesRepository;
    public FeatureType handle(UUID id) {
        return featureTypesRepository.findFeatureTypeById(id);
    }
}
