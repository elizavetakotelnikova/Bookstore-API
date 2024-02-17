package com.bookstore.app.entities.order.usecases.commands;

import com.bookstore.app.valueObjects.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateOrderCommand {
    private UUID id;
    private UUID userId;
    private Date date;
    private UUID shopId;
    private OrderState orderState;
    private List<UUID> productList;
}
