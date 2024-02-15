package com.bookstore.app.entities.user.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TokenResponse {
    String token;
}
