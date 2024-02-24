package com.bookstore.app.RepositoriesTests;

import com.bookstore.app.entities.product.Product;
import com.bookstore.app.entities.product.persistance.*;
import com.bookstore.app.entities.productFeature.persistance.*;
import com.bookstore.app.entities.productFeature.FeatureType;
import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.product.ProductType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepositoryTests {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );
    IProductsRepository productsRepository;
    IFeatureTypesRepository featureTypesRepository;
    IProductTypesRepository productTypesRepository;
    IProductFeaturesRepository productFeaturesRepository;
    Product testProduct;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection connectionProvider = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        var flyaway = setupFlyway(postgres);
        flyaway.clean();
        flyaway.migrate();
        productsRepository = new ProductsRepository(connectionProvider);
        featureTypesRepository = new FeatureTypesRepository(connectionProvider);
        productFeaturesRepository = new ProductFeaturesRepository(connectionProvider);
        productTypesRepository = new ProductTypesRepository(connectionProvider);
        setUpProduct();
    }
    private Flyway setupFlyway(PostgreSQLContainer container) {
        return new Flyway(
                Flyway.configure()
                        .locations("/db.migration")
                        .dataSource(container.getJdbcUrl(), container.getUsername(),
                                container.getPassword())
        );
    }

    private void setUpProduct() {
        var featureType = new FeatureType("color");
        featureTypesRepository.saveFeatureType(featureType);

        var feature = new ProductFeature(featureType, "yellow");
        productFeaturesRepository.saveProductFeature(feature);
        List<ProductFeature> featuresList = new ArrayList<>();
        featuresList.add(feature);

        var productType = new ProductType("Books");
        productTypesRepository.saveProductType(productType);

        testProduct = new Product(productType, "30 days", 100, featuresList);
    }

    @Test
    void testSavingProduct() {
        productsRepository.saveProduct(testProduct);
        var foundProduct = productsRepository.findProductById(testProduct.getId());
        assert(foundProduct.equals(testProduct));
    }
    @Test
    void testFindingProductsByCriteria() {
        productsRepository.saveProduct(testProduct);
        var secondProduct = new Product(testProduct.getType(), "Another book",
                testProduct.getPrice(), testProduct.getFeatures());
        productsRepository.saveProduct(secondProduct);
        var anotherType = new ProductType("Foreign literature");
        productTypesRepository.saveProductType(anotherType);
        var thirdProduct = new Product(anotherType, "New book", testProduct.getPrice(),
                testProduct.getFeatures());
        productsRepository.saveProduct(thirdProduct);
        var products = productsRepository.findProductsByCriteria(new FindCriteria(testProduct.getType().getId(), null));
        assert(products.size() == 2);
        assert(products.contains(testProduct));
        assert(products.contains(secondProduct));
        assert(!products.contains(thirdProduct));
    }

    @Test
    void testFindingProductsByName() {
        productsRepository.saveProduct(testProduct);
        var secondProduct = new Product(testProduct.getType(), "Another book",
                testProduct.getPrice(), testProduct.getFeatures());
        productsRepository.saveProduct(secondProduct);
        var products = productsRepository.findProductsByCriteria(new FindCriteria(null, "Another book"));
        assert(products.size() == 1);
        assert(!products.contains(testProduct));
        assert(products.contains(secondProduct));
    }

    @Test
    void testUpdateProduct() {
        productsRepository.saveProduct(testProduct);
        var secondProduct = new Product(testProduct.getId(), testProduct.getType(), "Another book",
                testProduct.getPrice(), testProduct.getFeatures());
        productsRepository.updateProduct(secondProduct);
        var foundProduct = productsRepository.findProductById(secondProduct.getId());
        assert(foundProduct.equals(secondProduct));
    }
    @Test
    void testDeleteProduct() {
        productsRepository.saveProduct(testProduct);
        productsRepository.deleteProductById(testProduct.getId());
        var foundProduct = productsRepository.findProductById(testProduct.getId());
        assert(foundProduct == null);
    }
}
