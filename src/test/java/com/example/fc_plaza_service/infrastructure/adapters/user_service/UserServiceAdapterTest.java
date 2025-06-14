package com.example.fc_plaza_service.infrastructure.adapters.user_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.infrastructure.adapters.user_service.feign.UserFeignClient;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DefaultServerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceAdapterTest {

  @InjectMocks private UserServiceAdapter userServiceAdapter;

  @Mock private UserFeignClient userFeignClient;

  @Test
  void existsLandlordById_ShouldReturnTrue() {
    Long userId = 1L;

    when(userFeignClient.findLandlordById(anyLong()))
        .thenReturn(new DefaultServerResponse<>(true, null));

    boolean result = userServiceAdapter.existsLandlordById(userId);

    assertTrue(result);
    verify(userFeignClient).findLandlordById(userId);
  }

  @Test
  void existsLandlordById_ShouldThrowBadRequest() {
    Long userId = 2L;

    when(userFeignClient.findLandlordById(anyLong()))
        .thenReturn(new DefaultServerResponse<>(null, null));

    assertThrows(BadRequest.class, () -> userServiceAdapter.existsLandlordById(userId));
    verify(userFeignClient).findLandlordById(userId);
  }

  @Test
  void doesLandlordBelongToEmail_ShouldReturnTrue() {
    Long userId = 1L;

    when(userFeignClient.doesLandlordBelongToEmail(userId))
        .thenReturn(new DefaultServerResponse<>(true, null));

    boolean result = userServiceAdapter.doesLandlordBelongToEmail(userId);

    assertTrue(result);
    verify(userFeignClient).doesLandlordBelongToEmail(userId);
  }

  @Test
  void doesLandlordBelongToEmail_ShouldThrowBadRequest() {
    Long userId = 2L;

    when(userFeignClient.doesLandlordBelongToEmail(userId))
        .thenReturn(new DefaultServerResponse<>(null, null));

    assertThrows(BadRequest.class, () -> userServiceAdapter.doesLandlordBelongToEmail(userId));
    verify(userFeignClient).doesLandlordBelongToEmail(userId);
  }

  @Test
  void getCurrentUserId_ShouldReturnUserId() {
    Long expectedUserId = 42L;

    when(userFeignClient.getCurrentUserId())
        .thenReturn(new DefaultServerResponse<>(expectedUserId, null));

    Long result = userServiceAdapter.getCurrentUserId();

    assertEquals(expectedUserId, result);
    verify(userFeignClient).getCurrentUserId();
  }

  @Test
  void getCurrentUserId_ShouldThrowBadRequest() {
    when(userFeignClient.getCurrentUserId()).thenReturn(new DefaultServerResponse<>(null, null));

    assertThrows(BadRequest.class, () -> userServiceAdapter.getCurrentUserId());
    verify(userFeignClient).getCurrentUserId();
  }

  @Test
  void getCurrentUserRestaurant_ShouldReturnRestaurantId() {
    Long expectedRestaurantId = 99L;

    when(userFeignClient.getEmployeeRestaurant())
        .thenReturn(new DefaultServerResponse<>(expectedRestaurantId, null));

    Long result = userServiceAdapter.getCurrentUserRestaurant();

    assertEquals(expectedRestaurantId, result);
    verify(userFeignClient).getEmployeeRestaurant();
  }

  @Test
  void getCurrentUserRestaurant_ShouldThrowBadRequest() {
    when(userFeignClient.getEmployeeRestaurant())
        .thenReturn(new DefaultServerResponse<>(null, null));

    assertThrows(BadRequest.class, () -> userServiceAdapter.getCurrentUserRestaurant());
    verify(userFeignClient).getEmployeeRestaurant();
  }
}
