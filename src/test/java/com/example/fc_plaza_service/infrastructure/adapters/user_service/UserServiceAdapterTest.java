package com.example.fc_plaza_service.infrastructure.adapters.user_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.infrastructure.adapters.user_service.feign.UserFeignClient;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DefaultServerResponse;
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
}
