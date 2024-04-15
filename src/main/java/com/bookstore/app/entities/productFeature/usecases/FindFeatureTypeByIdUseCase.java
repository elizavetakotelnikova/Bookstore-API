package com.bookstore.app.entities.productFeature.usecases;

import com.bookstore.app.entities.productFeature.persistance.IFeatureTypesRepository;
import com.bookstore.app.entities.productFeature.FeatureType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FindFeatureTypeByIdUseCase {
    private final IFeatureTypesRepository featureTypesRepository;
    public FeatureType handle(UUID id) {
        return featureTypesRepository.findFeatureTypeById(id);
    }
}
