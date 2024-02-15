package com.bookstore.app.entities.product.persistance;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.UUID;
@Data
public class FindCriteria {
    @Nullable
    UUID typeId;
}
