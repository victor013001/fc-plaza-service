package com.example.fc_plaza_service.util.data;

import static com.example.fc_plaza_service.util.data.RestaurantEntityData.getRestaurantEntity;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import java.util.concurrent.ThreadLocalRandom;

public class DishEntityData {
  private DishEntityData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static DishEntity getDishEntity() {
    Long dishId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);

    return DishEntity.builder()
        .id(dishId)
        .name("Test Dish")
        .price(10.0)
        .description("Delicious dish")
        .active(true)
        .category(null)
        .restaurant(getRestaurantEntity())
        .build();
  }
}
