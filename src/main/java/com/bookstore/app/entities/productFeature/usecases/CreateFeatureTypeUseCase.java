package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.entities.productFeature.FeatureType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateFeatureTypeUseCase {
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
