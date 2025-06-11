package com.example.fc_plaza_service.domain.api;

import com.example.fc_plaza_service.domain.model.Restaurant;
import java.util.List;

public interface RestaurantServicePort {
  void saveRestaurant(Restaurant restaurant);

  List<Restaurant> getRestaurants(Integer page, Integer size, String direction);
}
