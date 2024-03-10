package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.api.responses.IDResponse;
import com.bookstore.app.entities.product.api.responses.ProductResponse;
import com.bookstore.app.entities.product.dto.CreateProductDTO;
import com.bookstore.app.entities.product.dto.CreateProductFeatureDTO;
import com.bookstore.app.entities.product.dto.UpdateProductDTO;
import com.bookstore.app.entities.product.persistance.FindCriteria;
import com.bookstore.app.entities.product.usecases.commands.CreateProductCommand;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductCommand;
import com.bookstore.app.entities.product.usecases.productUseCases.*;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.exceptions.QueryException;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private CreateProductUseCase createProductUseCase;
    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;
    @Autowired
    private GetProductsByCriteriaUseCase getProductsByCriteriaUsecase;
    @Autowired
    private UpdateProductUseCase updateProductUseCase;
    @Autowired
    private DeleteProductUseCase deleteProductUseCase;
    @PostMapping("/products")
    public IDResponse createProduct(@RequestBody CreateProductDTO providedProduct) throws InvalidKeySpecException {
        List<Pair<UUID, String>> featuresList = new ArrayList<>();
        for (CreateProductFeatureDTO each : providedProduct.getFeatures()) {
            featuresList.add(new Pair<>(each.getFeatureTypeId(), each.getValue()));
        }
        var command = new CreateProductCommand(providedProduct.getTypeId(), providedProduct.getName(),
                providedProduct.getPrice(), featuresList);
        try {
            var product = createProductUseCase.handle(command);
            return new IDResponse(product.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @GetMapping("/product/{productId}")
    public ProductResponse getProductById(@PathVariable("productId") UUID productId) {
        var product = getProductByIdUseCase.handle(productId);
        return new ProductResponse(product.getId(), product.getType(),
                product.getName(), product.getPrice(), product.getFeatures());
    }
    @GetMapping("/products/")
    public List<ProductResponse> getProductByCriteria(@Param("typeId") UUID typeId, @Param("name") String name) {
        var criteria = new FindCriteria(null, null);
        if (typeId != null) criteria.setTypeId(typeId);
        if (name != null) criteria.setName(name);

        var products = getProductsByCriteriaUsecase.handle(criteria);
        var result = new ArrayList<ProductResponse>();
        for (Product each : products) {
            result.add(new ProductResponse(each.getId(), each.getType(), each.getName(), each.getPrice(), each.getFeatures()));
        }
        return result;
    }

    @PutMapping("/product/{productId}")
    public IDResponse updateProduct(@PathVariable("productId") UUID id, @RequestBody UpdateProductDTO providedProduct) {
        var command = new UpdateProductCommand(providedProduct.getType(),
                providedProduct.getName(), providedProduct.getPrice(), providedProduct.getFeatures());
        try {
            var product = updateProductUseCase.handle(command);
            return new IDResponse(product.getId());
        }
        catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
    }

    @DeleteMapping("/product/{productId}")
    public void updateProduct(@PathVariable("productId") UUID id) {
        try {
            deleteProductUseCase.handle(id);
        } catch (QueryException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
