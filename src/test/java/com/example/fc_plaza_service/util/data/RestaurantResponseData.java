package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.RestaurantResponse;

public class RestaurantResponseData {
  private RestaurantResponseData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static RestaurantResponse getRestaurantResponse() {
    return new RestaurantResponse("Pasta Place", "https://example.com/logo.png");
  }
}
