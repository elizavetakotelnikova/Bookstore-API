package com.bookstore.app.entities.productFeature.api;
import com.bookstore.app.entities.productFeature.api.responses.FeatureDetailsResponse;
import com.bookstore.app.entities.productFeature.api.responses.IDResponse;
import com.bookstore.app.entities.productFeature.dto.CreateFeatureTypeDTO;
import com.bookstore.app.entities.productFeature.usecases.*;
import com.bookstore.app.entities.productFeature.usecases.commands.UpdateCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.exceptions.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@RestController
public class FeatureTypesController {
    @Autowired
    private CreateFeatureTypeUseCase createFeatureTypeUseCase;
    @Autowired
    private FindFeatureTypeByIdUseCase findFeatureByIdUseCase;
    @Autowired
    private FindFeatureTypeByNameUseCase findFeatureTypeByNameUseCase;
    @Autowired
    private UpdateFeatureTypeByIdUseCase updateFeatureTypeByIdUseCase;
    @Autowired
    private DeleteFeatureTypeUseCase deleteFeatureTypeUseCase;

    @PostMapping("/featureTypes")
    public IDResponse createFeatureType(@RequestBody CreateFeatureTypeDTO providedFeatureType) throws InvalidKeySpecException {
        try {
            var FeatureType = createFeatureTypeUseCase.handle(providedFeatureType.getName());
            return new IDResponse(FeatureType.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/featureTypes/{featureTypeId}")
    public FeatureDetailsResponse getFeatureTypeById(@PathVariable("featureTypeId") UUID featureTypeId) {
        var featureType = findFeatureByIdUseCase.handle(featureTypeId);
        return new FeatureDetailsResponse(featureType.getId(), featureType.getName());
    }
    @GetMapping("/featureTypes/")
    public FeatureDetailsResponse getFeatureTypeByName(@Param("name") String name) {
        var featureType = findFeatureTypeByNameUseCase.handle(name);
        return new FeatureDetailsResponse(featureType.getId(), featureType.getName());
    }

    @PutMapping("/featureType/{featureTypeId}")
    public IDResponse updateFeatureType(@PathVariable("featureTypeId") UUID id, @RequestBody CreateFeatureTypeDTO providedFeatureType) {
        try {
            var featureType = updateFeatureTypeByIdUseCase.handle(new UpdateCommand(id, providedFeatureType.getName()));
            return new IDResponse(featureType.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/featureType/{featureTypeId}")
    public void updateFeatureType(@PathVariable("featureTypeId") UUID id) {
        try {
            deleteFeatureTypeUseCase.handle(id);
        } catch (QueryException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
