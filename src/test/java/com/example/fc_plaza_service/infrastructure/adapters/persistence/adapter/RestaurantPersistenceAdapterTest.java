package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_plaza_service.util.data.RestaurantData.getValidRestaurant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.RestaurantEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.RestaurantEntityMapperImpl;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantPersistenceAdapterTest {

  @InjectMocks private RestaurantPersistenceAdapter restaurantPersistenceAdapter;

  @Spy private RestaurantEntityMapper restaurantEntityMapper = new RestaurantEntityMapperImpl();

  @Mock private RestaurantRepository restaurantRepository;

  @Test
  void saveRestaurant_ShouldSaveEntity() {
    var restaurant = getValidRestaurant();

    when(restaurantRepository.save(any(RestaurantEntity.class)))
        .thenReturn(RestaurantEntity.builder().build());

    restaurantPersistenceAdapter.saveRestaurant(restaurant);

    verify(restaurantEntityMapper).toEntity(restaurant);
    verify(restaurantRepository).save(any(RestaurantEntity.class));
  }

  @Test
  void existsByNIT_ShouldReturnTrue() {
    String nit = "NIT123";
    when(restaurantRepository.existsByNit(nit)).thenReturn(true);

    boolean result = restaurantPersistenceAdapter.existsByNIT(nit);

    assertTrue(result);
    verify(restaurantRepository).existsByNit(nit);
  }

  @Test
  void existsByPhone_ShouldReturnFalse() {
    String phone = "1234567890";
    when(restaurantRepository.existsByPhone(phone)).thenReturn(false);

    boolean result = restaurantPersistenceAdapter.existsByPhone(phone);

    assertFalse(result);
    verify(restaurantRepository).existsByPhone(phone);
  }

  @Test
  void existsById_ShouldReturnTrue() {
    Long restaurantId = 1L;
    when(restaurantRepository.existsById(restaurantId)).thenReturn(true);

    boolean result = restaurantPersistenceAdapter.existsById(restaurantId);

    assertTrue(result);
    verify(restaurantRepository).existsById(restaurantId);
  }

  @Test
  void getLandlordId_ShouldReturnUserId() {
    Long restaurantId = 1L;
    Long expectedUserId = 100L;
    when(restaurantRepository.findUserIdByRestaurantId(restaurantId)).thenReturn(expectedUserId);

    Long result = restaurantPersistenceAdapter.getLandlordId(restaurantId);

    assertEquals(expectedUserId, result);
    verify(restaurantRepository).findUserIdByRestaurantId(restaurantId);
  }
}
