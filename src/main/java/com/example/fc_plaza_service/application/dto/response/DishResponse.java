package com.example.fc_plaza_service.application.dto.response;

public record DishResponse(
    String name, Double price, String description, String imageUrl, String categoryName) {}
