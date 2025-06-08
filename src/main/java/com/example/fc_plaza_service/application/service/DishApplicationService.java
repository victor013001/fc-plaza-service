package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;

public interface DishApplicationService {
  void createDish(Long restaurantId, DishRequest dishRequest);
}
