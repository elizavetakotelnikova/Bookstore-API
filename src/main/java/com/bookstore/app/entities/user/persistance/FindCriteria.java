package com.bookstore.app.entities.user.persistance;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FindCriteria {
    private String phoneNumber;
    @Nullable
    private java.time.LocalDate birthday;
}
