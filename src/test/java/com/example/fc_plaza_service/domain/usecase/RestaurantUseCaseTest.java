package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.RestaurantData.getValidRestaurant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.api.UserServicePort;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Restaurant;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

  @InjectMocks private RestaurantUseCase restaurantUseCase;

  @Mock private RestaurantPersistencePort restaurantPersistencePort;

  @Mock private UserServicePort userServicePort;

  @Test
  void saveRestaurant_success() {
    var restaurant = getValidRestaurant();

    when(restaurantPersistencePort.existsByNIT(anyString())).thenReturn(false);
    when(restaurantPersistencePort.existsByPhone(anyString())).thenReturn(false);
    when(userServicePort.existsLandlordById(anyLong())).thenReturn(true);

    restaurantUseCase.saveRestaurant(restaurant);

    verify(restaurantPersistencePort).existsByNIT(restaurant.nit());
    verify(restaurantPersistencePort).existsByPhone(restaurant.phone());
    verify(userServicePort).existsLandlordById(restaurant.userId());
    verify(restaurantPersistencePort).saveRestaurant(restaurant);
  }

  @Test
  void saveRestaurant_nitAlreadyExists() {
    var restaurant = getValidRestaurant();

    when(restaurantPersistencePort.existsByNIT(anyString())).thenReturn(true);

    assertThrows(BadRequest.class, () -> restaurantUseCase.saveRestaurant(restaurant));

    verify(restaurantPersistencePort).existsByNIT(restaurant.nit());
    verify(restaurantPersistencePort, never()).existsByPhone(anyString());
    verify(restaurantPersistencePort, never()).saveRestaurant(any());
    verifyNoInteractions(userServicePort);
  }

  @Test
  void saveRestaurant_phoneAlreadyExists() {
    var restaurant = getValidRestaurant();

    when(restaurantPersistencePort.existsByNIT(anyString())).thenReturn(false);
    when(restaurantPersistencePort.existsByPhone(anyString())).thenReturn(true);

    assertThrows(BadRequest.class, () -> restaurantUseCase.saveRestaurant(restaurant));

    verify(restaurantPersistencePort).existsByNIT(restaurant.nit());
    verify(restaurantPersistencePort).existsByPhone(restaurant.phone());
    verify(restaurantPersistencePort, never()).saveRestaurant(any());
    verifyNoInteractions(userServicePort);
  }

  @Test
  void saveRestaurant_landlordDoesNotExist() {
    var restaurant = getValidRestaurant();

    when(restaurantPersistencePort.existsByNIT(anyString())).thenReturn(false);
    when(restaurantPersistencePort.existsByPhone(anyString())).thenReturn(false);
    when(userServicePort.existsLandlordById(anyLong())).thenReturn(false);

    assertThrows(BadRequest.class, () -> restaurantUseCase.saveRestaurant(restaurant));

    verify(restaurantPersistencePort).existsByNIT(restaurant.nit());
    verify(restaurantPersistencePort).existsByPhone(restaurant.phone());
    verify(restaurantPersistencePort, never()).saveRestaurant(any(Restaurant.class));
    verify(userServicePort).existsLandlordById(restaurant.userId());
  }
}
