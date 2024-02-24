package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.productFeature.FeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindFeatureTypeByNameUseCase {
    @Autowired
    private IFeatureTypesRepository featureTypesRepository;
    public FeatureType handle(String name) {
        return featureTypesRepository.findFeatureByName(name);
    }
}


