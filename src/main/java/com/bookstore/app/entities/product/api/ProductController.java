package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.api.responses.ProductIDResponse;
import com.bookstore.app.entities.product.api.responses.ProductResponse;
import com.bookstore.app.entities.product.api.viewModels.CreateProductViewModel;
import com.bookstore.app.entities.product.api.viewModels.UpdateProductViewModel;
import com.bookstore.app.entities.product.usecases.*;
import com.bookstore.app.entities.product.persistance.FindCriteria;
import com.bookstore.app.entities.product.usecases.commands.CreateProductCommand;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductCommand;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.exceptions.QueryException;
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
    public ProductIDResponse createProduct(@RequestBody CreateProductViewModel providedProduct) throws InvalidKeySpecException {
        var command = new CreateProductCommand(providedProduct.getType(), providedProduct.getName(),
                providedProduct.getPrice(), providedProduct.getFeatures());
        try {
            var product = createProductUseCase.handle(command);
            return new ProductIDResponse(product.getId());
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
    public ProductIDResponse updateProduct(@PathVariable("productId") UUID id, @RequestBody UpdateProductViewModel providedProduct) {
        var command = new UpdateProductCommand(providedProduct.getType(),
                providedProduct.getName(), providedProduct.getPrice(), providedProduct.getFeatures());
        try {
            var product = updateProductUseCase.handle(command);
            return new ProductIDResponse(product.getId());
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
