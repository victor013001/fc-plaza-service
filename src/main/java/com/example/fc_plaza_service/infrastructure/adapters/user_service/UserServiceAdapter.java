package com.example.fc_plaza_service.infrastructure.adapters.user_service;

import com.example.fc_plaza_service.domain.api.UserServicePort;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.infrastructure.adapters.user_service.feign.UserFeignClient;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
  private static final String LOG_PREFIX = "[USER_SERVICE_ADAPTER] >>> ";

  private final UserFeignClient userFeignClient;

  @Override
  public boolean existsLandlordById(Long userId) {
    log.info("{} Checking if user with id: {} exists and is a landlord", LOG_PREFIX, userId);
    return Optional.of(userFeignClient.findLandlordById(userId))
        .map(DefaultServerResponse::data)
        .orElseThrow(BadRequest::new);
  }

  @Override
  public boolean doesLandlordBelongToEmail(Long userId) {
    log.info("{} Checking if user with id: {} belongs to request email", LOG_PREFIX, userId);
    return Optional.of(userFeignClient.doesLandlordBelongToEmail(userId))
        .map(DefaultServerResponse::data)
        .orElseThrow(BadRequest::new);
  }
}
