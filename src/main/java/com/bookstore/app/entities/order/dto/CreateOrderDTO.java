package com.bookstore.app.entities.order.dto;

import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateOrderDTO {
    private UUID userId;
    private java.time.LocalDate date;
    private UUID shopId;
    private OrderState orderState;
    private List<UUID> productList;
}
