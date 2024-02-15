package com.bookstore.app.entities.user.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@AllArgsConstructor
@Data
public class UserIDResponse {
    UUID id;
}
