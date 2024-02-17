package com.bookstore.app.entities.order.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderIDResponse {
    public UUID id;
}
