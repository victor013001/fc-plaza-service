package com.example.fc_plaza_service.infrastructure.adapters.persistence.entity;

public interface DishProjection {
  Long getId();

  String getName();

  Double getPrice();

  String getDescription();

  String getImageUrl();

  Boolean getActive();

  Long getRestaurantId();

  String getCategoryName();
}
