package com.bookstore.app.valueObjects;

import com.bookstore.app.entities.order.Order;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStateConverter implements AttributeConverter<OrderState, String> {
    @Override
    public String convertToDatabaseColumn(OrderState state) {
        return state.name().toLowerCase();
    }

    @Override
    public OrderState convertToEntityAttribute(String dbData) {
        return OrderState.valueOf(dbData);
    }
}
