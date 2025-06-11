package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.Restaurant;
import java.util.List;

public interface RestaurantPersistencePort {
  void saveRestaurant(Restaurant restaurant);

  boolean existsByNIT(String nit);

  boolean existsByPhone(String phone);

  boolean existsById(Long restaurantId);

  Long getLandlordId(Long restaurantId);

  List<Restaurant> getRestaurants(Integer page, Integer size, String direction);
}
