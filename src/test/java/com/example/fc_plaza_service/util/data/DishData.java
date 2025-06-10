package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.model.Dish;

public class DishData {
  private DishData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static Dish getValidDish() {
    return new Dish("Pizza", 12.5, "Cheese and tomato", "http://img.com/pizza.jpg", 1L, 1L);
  }

  public static Dish getInvalidDish() {
    return new Dish("Pizza", 12.5, "Cheese and tomato", "http://img.com/pizza.jpg", 1L, 1L);
  }
}
