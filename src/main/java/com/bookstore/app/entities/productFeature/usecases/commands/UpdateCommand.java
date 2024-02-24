package com.bookstore.app.entities.productFeature.usecases.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateCommand {
    private UUID id;
    private String name;
}
