package com.example.fc_plaza_service.domain.model;

public record Dish(
    String name,
    Double price,
    String description,
    String imageUrl,
    Long restaurantId,
    Long categoryId) {}
