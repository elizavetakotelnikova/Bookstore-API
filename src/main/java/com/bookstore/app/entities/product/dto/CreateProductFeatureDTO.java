package com.bookstore.app.entities.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductFeatureDTO {
    private UUID featureTypeId;
    private String value;
}
