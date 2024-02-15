package com.bookstore.app.entities.product.usecases.commands;

import com.bookstore.app.models.ProductFeature;
import com.bookstore.app.models.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateProductCommand {
    private ProductType type;
    private String name;
    private int price;
    private List<ProductFeature> features;
}
