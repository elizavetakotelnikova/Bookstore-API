package com.bookstore.app.entities.product.api;

import com.bookstore.app.entities.product.persistance.IProductsRepository;
import com.bookstore.app.entities.user.persistance.FindCriteria;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    private IProductsRepository productsRepository;
    @PostMapping("/products")
    public ProductIDResponse createProduct(@RequestBody CreateProductViewModel providedProduct) throws InvalidKeySpecException {
        var productMapper = new ProductMapper();
        var product = productMapper.mapCreateViewModelToProduct(providedProduct);
        product = productsRepository.save(product);
        return new ProductIDResponse(product.getId());
    }

    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable("productId") UUID productId) {
        return usersRepository.findProductById(productId);
    }
    @GetMapping("/products/")
    public List<Product> getProductByCriteria(@Param("birthday") String date, @Param("phoneNumber") String phoneNumber) {
        var criteria = new FindCriteria();
        try {
            var newDate = LocalDate.parse(date);
        }
        catch (Exception e) {
            criteria.setBirthday(null);
        }
        if (!phoneNumber.isEmpty()) criteria.setPhoneNumber(phoneNumber);

        return usersRepository.findProductByCriteria(criteria);
    }

    @PutMapping("/product/{productId}")
    public ProductIDResponse updateProduct(@PathVariable("productId") UUID id, @RequestBody CreateProductViewModel providedProduct) {
        var userMapper = new ProductMapper();
        var user = userMapper.MapProductViewModelToProduct(providedProduct);
        user = usersRepository.updateProduct(user);
        return new ProductIDResponse(user.getId());
    }

    @DeleteMapping("/product/{productId}")
    public void updateProduct(@PathVariable("productId") UUID id) {
        usersRepository.deleteProductById(id);
    }
}
