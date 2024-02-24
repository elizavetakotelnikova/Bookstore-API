package com.bookstore.app.entities.product.usecases.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateProductTypeCommand {
    private UUID id;
    private String name;
}
