package com.example.fc_plaza_service.infrastructure.entrypoint.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DishRequest(
    @NotBlank(message = "Name is mandatory") String name,
    @NotNull(message = "Price is mandatory")
        @Positive
        @DecimalMin(value = "0.1", message = "Value must be greater than zero")
        Double price,
    @NotBlank(message = "Description is mandatory") String description,
    @NotBlank(message = "Image Url is mandatory") String imageUrl,
    @NotNull(message = "Category ID is mandatory") Long categoryId) {}
