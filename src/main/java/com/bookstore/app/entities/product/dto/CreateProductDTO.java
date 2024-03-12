package com.bookstore.app.entities.product.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    private UUID typeId;
    private String name;
    private int price;
    private List<CreateProductFeatureDTO> features;
}
