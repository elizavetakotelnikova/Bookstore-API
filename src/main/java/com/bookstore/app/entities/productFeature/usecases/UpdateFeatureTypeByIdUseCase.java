package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.productFeature.usecases.commands.UpdateCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.entities.productFeature.FeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateFeatureTypeByIdUseCase {
    @Autowired
    private IFeatureTypesRepository featureTypesRepository;
    public FeatureType handle(UpdateCommand command) throws IncorrectArgumentsException {
        if (featureTypesRepository.findFeatureByName(command.getName()) != null) {
            throw new IncorrectArgumentsException();
        }
        var featureType = new FeatureType(command.getId(), command.getName());
        featureTypesRepository.updateFeatureType(featureType);
        return featureType;
    }
}
