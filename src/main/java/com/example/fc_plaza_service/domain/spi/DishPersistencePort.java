package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.Dish;

public interface DishPersistencePort {
  void saveDish(Dish dish);

  boolean existsByName(String name, Long restaurantId);
}
