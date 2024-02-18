package com.bookstore.app.entities.order.persistance;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FindCriteria {
    @Nullable
    private UUID userId;
    @Nullable
    private LocalDate date;
    public FindCriteria() {
        userId = null;
        date = null;
    }
}
