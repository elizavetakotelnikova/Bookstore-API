package com.bookstore.app.entities.product.api.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductTypeRequest {
    private String name;
}
