package com.bookstore.app.entities.product.dto;

import com.bookstore.app.entities.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;

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
