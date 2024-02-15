package com.bookstore.app.entities.product.api.viewModels;

import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateProductViewModel {
    private ProductType type;
    private String name;
    private int price;
    private List<ProductFeature> features;
}
