package com.bookstore.app.entities.productFeature.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IDResponse {
    private UUID id;
}
