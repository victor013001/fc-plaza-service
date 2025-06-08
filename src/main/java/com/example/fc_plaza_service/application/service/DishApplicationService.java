package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishUpdateRequest;

public interface DishApplicationService {
  void createDish(Long restaurantId, DishRequest dishRequest);

  void updateDish(Long restaurantId, Long dishId, DishUpdateRequest dishUpdateRequest);
}
