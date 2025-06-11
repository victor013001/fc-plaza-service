package com.example.fc_plaza_service.infrastructure.entrypoint.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequest(
    @NotEmpty(message = "Dishes are mandatory.") @Valid List<OrderDishRequest> dishes) {}
