package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.RestaurantRequest;

public interface RestaurantApplicationService {
  void createRestaurant(RestaurantRequest restaurantRequest);
}
