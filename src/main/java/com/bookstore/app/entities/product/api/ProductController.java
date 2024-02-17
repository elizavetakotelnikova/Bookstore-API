package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.api.responses.ProductIDResponse;
import com.bookstore.app.entities.product.api.viewModels.CreateProductViewModel;
import com.bookstore.app.entities.product.usecases.*;
import com.bookstore.app.entities.product.persistance.FindCriteria;
import com.bookstore.app.entities.product.usecases.commands.CreateProductCommand;
import com.bookstore.app.entities.product.usecases.commands.UpdateProductCommand;
import com.bookstore.app.exceptions.QueryException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private CreateProductUseCase createProductUseCase;
    private GetProductByIdUseCase getProductByIdUseCase;
    private GetProductsByCriteriaUseCase getProductsByCriteriaUsecase;
    private UpdateProductUseCase updateProductUseCase;
    private DeleteProductUseCase deleteProductUseCase;
    @PostMapping("/products")
    public ProductIDResponse createProduct(@RequestBody CreateProductViewModel providedProduct) throws InvalidKeySpecException {
        var command = new CreateProductCommand(providedProduct.getType(), providedProduct.getName(),
                providedProduct.getPrice(), providedProduct.getFeatures());
        var product = createProductUseCase.handle(command);
        return new ProductIDResponse(product.getId());
    }

    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable("productId") UUID productId) {
        return getProductByIdUseCase.handle(productId);
    }
    @GetMapping("/products/")
    public List<Product> getProductByCriteria(@Param("typeId") UUID typeId, @Param("name") String name) {
        var criteria = new FindCriteria(null, null);
        if (typeId != null) criteria.setTypeId(typeId);
        if (name != null) criteria.setName(name);

        return getProductsByCriteriaUsecase.handle(criteria);
    }

    @PutMapping("/product/{productId}")
    public ProductIDResponse updateProduct(@PathVariable("productId") UUID id, @RequestBody CreateProductViewModel providedProduct) {
        var command = new UpdateProductCommand(providedProduct.getType(),
                providedProduct.getName(), providedProduct.getPrice(), providedProduct.getFeatures());
        var product = updateProductUseCase.handle(command);
        return new ProductIDResponse(product.getId());
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
