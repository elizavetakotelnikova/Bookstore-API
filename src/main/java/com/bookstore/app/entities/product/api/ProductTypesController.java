package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.api.responses.IDResponse;
import com.bookstore.app.entities.product.api.responses.ProductTypeResponse;
import com.bookstore.app.entities.product.api.viewModels.CreateProductTypeRequest;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductTypeCommand;
import com.bookstore.app.entities.product.usecases.productTypeUseCases.*;
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
public class ProductTypesController {
    @Autowired
    private CreateProductTypeUseCase createProductTypeUseCase;
    @Autowired
    private FindProductTypeByIdUseCase findFeatureByIdUseCase;
    @Autowired
    private FindProductTypeByNameUseCase findProductTypeByNameUseCase;
    @Autowired
    private UpdateProductTypeUseCase updateProductTypeUseCase;
    @Autowired
    private DeleteProductTypeUseCase deleteProductTypeUseCase;

    @PostMapping("/productTypes")
    public IDResponse createProductType(@RequestBody CreateProductTypeRequest providedProductType) throws InvalidKeySpecException {
        try {
            var productType = createProductTypeUseCase.handle(providedProductType.getName());
            return new IDResponse(productType.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/productTypes/{productTypeId}")
    public ProductTypeResponse getProductTypeById(@PathVariable("productTypeId") UUID productTypeId) {
        var productType = findFeatureByIdUseCase.handle(productTypeId);
        return new ProductTypeResponse(productType.getId(), productType.getName());
    }
    @GetMapping("/productTypes/")
    public ProductTypeResponse getProductTypeByName(@Param("name") String name) {
        var productType = findProductTypeByNameUseCase.handle(name);
        return new ProductTypeResponse(productType.getId(), productType.getName());
    }

    @PutMapping("/productType/{productTypeId}")
    public IDResponse updateProductType(@PathVariable("productTypeId") UUID id, @RequestBody CreateProductTypeRequest providedProductType) {
        try {
            var productType = updateProductTypeUseCase.handle(new UpdateProductTypeCommand(id, providedProductType.getName()));
            return new IDResponse(productType.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/productType/{productTypeId}")
    public void updateProductType(@PathVariable("productTypeId") UUID id) {
        try {
            deleteProductTypeUseCase.handle(id);
        } catch (QueryException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
