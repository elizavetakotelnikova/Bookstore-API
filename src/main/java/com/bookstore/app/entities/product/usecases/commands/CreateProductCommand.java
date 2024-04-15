package com.bookstore.app.entities.product.usecases.commands;

import com.bookstore.app.entities.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateProductCommand {
    private UUID typeId;
    private String name;
    private int price;
    private List<Pair<UUID, String>> features;
}
