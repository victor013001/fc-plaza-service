package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.model.DishCategory;

public class DishCategoryData {
  private DishCategoryData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static DishCategory getDishCategory() {
    return new DishCategory(
        "Pizza", 12.5, "Cheese and tomato", "http://img.com/pizza.jpg", "Fast Food");
  }
}
