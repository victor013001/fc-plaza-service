package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishUpdateRequest;

public class DishRequestData {
  private DishRequestData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static DishRequest getValidDishRequest() {
    return new DishRequest("Pizza", 12.5, "Cheese and tomato", "http://img.com/pizza.jpg", 1L);
  }

  public static DishRequest getInvalidDishRequest() {
    return new DishRequest("Pizza", 0.0, "Cheese and tomato", "http://img.com/pizza.jpg", 1L);
  }

  public static DishUpdateRequest getValidDishUpdateRequest() {
    return new DishUpdateRequest(14.0, "Updated description");
  }

  public static DishUpdateRequest getInvalidDishUpdateRequest() {
    return new DishUpdateRequest(0.0, "Updated description");
  }
}
