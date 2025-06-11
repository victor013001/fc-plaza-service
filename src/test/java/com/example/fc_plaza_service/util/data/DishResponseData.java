package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.DishResponse;

public class DishResponseData {
  private DishResponseData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static DishResponse getValidDishResponse() {
    return new DishResponse(
        "Pizza", 12.5, "Cheese and tomato", "http://img.com/pizza.jpg", "Fast Food");
  }
}
