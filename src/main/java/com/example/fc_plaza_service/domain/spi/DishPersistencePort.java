package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.Dish;

public interface DishPersistencePort {
  void saveDish(Dish dish);

  boolean existsByName(String name, Long restaurantId);

  boolean existsById(Long dishId, Long restaurantId);

  void updateDish(Dish dish, Long dishId);

  void updateActive(Long dishId, boolean active);
}
