package com.example.fc_plaza_service.domain.api;

import com.example.fc_plaza_service.domain.model.Restaurant;

public interface RestaurantServicePort {
  void saveRestaurant(Restaurant restaurant);
}
