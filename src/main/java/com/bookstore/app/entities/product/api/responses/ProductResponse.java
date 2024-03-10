package com.bookstore.app.entities.product.api.responses;

import com.bookstore.app.entities.productFeature.ProductFeature;
import com.bookstore.app.entities.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private UUID id;
    private ProductType type;
    private String name;
    private int price;
    private List<ProductFeature> features;
}
