package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.RestaurantServicePort;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Restaurant;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements RestaurantServicePort {

  private final RestaurantPersistencePort restaurantPersistencePort;
  private final UserServicePort userServicePort;

  @Override
  public void saveRestaurant(Restaurant restaurant) {
    validNIT(restaurant.nit());
    validPhone(restaurant.phone());
    validLandlordId(restaurant.userId());
    restaurantPersistencePort.saveRestaurant(restaurant);
  }

  @Override
  public List<Restaurant> getRestaurants(Integer page, Integer size, String direction) {
    validPageRequest(page, size, direction);
    return restaurantPersistencePort.getRestaurants(page, size, direction);
  }

  private void validPageRequest(Integer page, Integer size, String direction) {
    if (page <= 0 || size < 0 || !isValidDirection(direction)) {
      throw new BadRequest();
    }
  }

  private boolean isValidDirection(String direction) {
    return "asc".equalsIgnoreCase(direction) || "desc".equalsIgnoreCase(direction);
  }

  private void validNIT(String nit) {
    if (restaurantPersistencePort.existsByNIT(nit)) {
      throw new BadRequest();
    }
  }

  private void validPhone(String phone) {
    if (restaurantPersistencePort.existsByPhone(phone)) {
      throw new BadRequest();
    }
  }

  private void validLandlordId(Long userId) {
    if (!userServicePort.existsLandlordById(userId)) {
      throw new BadRequest();
    }
  }
}
