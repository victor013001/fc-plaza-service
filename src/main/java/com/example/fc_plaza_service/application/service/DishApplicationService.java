package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishUpdateRequest;

public interface DishApplicationService {
  void createDish(Long restaurantId, DishRequest dishRequest);

  void updateDish(Long restaurantId, Long dishId, DishUpdateRequest dishUpdateRequest);

  void updateStatusDish(Long restaurantId, Long dishId, ProductStatus status);
}
