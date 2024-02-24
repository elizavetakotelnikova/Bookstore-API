package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.entities.productFeature.FeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFeatureTypeUseCase {
    @Autowired
    private IFeatureTypesRepository featureTypesRepository;
    public FeatureType handle(String name) throws IncorrectArgumentsException {
        if (featureTypesRepository.findFeatureByName(name) != null) {
            throw new IncorrectArgumentsException();
        }
        var featureType = new FeatureType(name);
        featureTypesRepository.saveFeatureType(featureType);
        return featureType;
    }
}
