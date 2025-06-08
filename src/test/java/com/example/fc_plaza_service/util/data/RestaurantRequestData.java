package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.RestaurantRequest;

public class RestaurantRequestData {
  private RestaurantRequestData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static RestaurantRequest getValidRestaurantRequest() {
    return new RestaurantRequest(
        "Pasta Place",
        "123456789-0",
        "123 Main St",
        "3001234567",
        "https://example.com/logo.png",
        1L
    );
  }

  public static RestaurantRequest getInvalidRestaurantRequest() {
    return new RestaurantRequest(
        "",
        "",
        "",
        "",
        null,
        null
    );
  }
}
