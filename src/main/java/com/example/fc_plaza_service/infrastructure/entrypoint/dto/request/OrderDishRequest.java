package com.example.fc_plaza_service.infrastructure.entrypoint.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderDishRequest(
    @NotNull(message = "Dish id is mandatory.") Long dishId,
    @Positive(message = "Dish quantity must be positive") Integer quantity) {}
