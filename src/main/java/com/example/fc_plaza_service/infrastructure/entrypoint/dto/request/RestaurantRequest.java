package com.example.fc_plaza_service.infrastructure.entrypoint.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(
    @NotBlank(message = "Name is mandatory") String name,
    @NotBlank(message = "NIT is mandatory") String nit,
    @NotBlank(message = "Address is mandatory") String address,
    @NotBlank(message = "Phone is mandatory") String phone,
    @NotBlank(message = "Url logo is mandatory") String logoUrl,
    @NotNull(message = "User id is mandatory") Long userId) {}
