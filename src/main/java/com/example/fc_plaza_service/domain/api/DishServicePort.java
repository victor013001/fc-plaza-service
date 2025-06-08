package com.example.fc_plaza_service.domain.api;

import com.example.fc_plaza_service.domain.model.Dish;

public interface DishServicePort {

  void saveDish(Dish dish);

  void updateDish(Dish dish, Long dishId);
}
