package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.model.Restaurant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RestaurantData {
  private RestaurantData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static Restaurant getValidRestaurant() {
    var restaurantId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    var nit = "NIT-" + restaurantId;
    String phone =
        String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000L, 10000000000000L));
    return new Restaurant(
        "Pasta Place", nit, "123 Main St", phone, "https://example.com/logo.png", 1L);
  }

  public static List<Restaurant> getRestaurants() {
    return List.of(getValidRestaurant(), getValidRestaurant(), getValidRestaurant());
  }

  public static Restaurant getInvalidRestaurant() {
    return new Restaurant("", "", "", "", null, null);
  }
}
