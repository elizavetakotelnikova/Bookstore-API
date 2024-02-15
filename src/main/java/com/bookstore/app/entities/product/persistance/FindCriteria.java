package com.bookstore.app.entities.product.persistance;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class FindCriteria {
    @Nullable
    UUID typeId;
    String name;
}
