package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.model.Restaurant;

public class RestaurantData {
  private RestaurantData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static Restaurant getValidRestaurant() {
    return new Restaurant(
        "Pasta Place",
        "123456789-0",
        "123 Main St",
        "3001234567",
        "https://example.com/logo.png",
        1L);
  }

  public static Restaurant getInvalidRestaurant() {
    return new Restaurant("", "", "", "", null, null);
  }
}
