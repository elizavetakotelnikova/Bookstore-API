package com.bookstore.app.entities.product.api.viewModels;

import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateProductViewModel {
    private ProductType type;
    private String name;
    private int price;
    private List<ProductFeature> features;
}
