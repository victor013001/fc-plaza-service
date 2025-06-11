package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.RestaurantRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.RestaurantResponse;
import java.util.List;

public interface RestaurantApplicationService {
  void createRestaurant(RestaurantRequest restaurantRequest);

  List<RestaurantResponse> getRestaurants(Integer page, Integer size, String direction);
}
