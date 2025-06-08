package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.DishServicePort;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Dish;
import com.example.fc_plaza_service.domain.spi.DishPersistencePort;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements DishServicePort {

  private final DishPersistencePort dishPersistencePort;
  private final RestaurantPersistencePort restaurantPersistencePort;

  @Override
  public void saveDish(Dish dish) {
    validRestaurantId(dish.restaurantId());
    validName(dish.name(), dish.restaurantId());
    dishPersistencePort.saveDish(dish);
  }

  @Override
  public void updateDish(Dish dish, Long dishId) {
    validRestaurantId(dish.restaurantId());
    validDishId(dishId, dish.restaurantId());
    dishPersistencePort.updateDish(dish, dishId);
  }

  private void validDishId(Long dishId, Long restaurantId) {
    if (!dishPersistencePort.existsById(dishId, restaurantId)) {
      throw new BadRequest();
    }
  }

  private void validRestaurantId(Long restaurantId) {
    if (!restaurantPersistencePort.existsById(restaurantId)) {
      throw new BadRequest();
    }
  }

  private void validName(String name, Long restaurantId) {
    if (dishPersistencePort.existsByName(name, restaurantId)) {
      throw new BadRequest();
    }
  }
}
