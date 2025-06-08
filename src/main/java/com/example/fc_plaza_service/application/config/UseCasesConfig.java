package com.example.fc_plaza_service.application.config;

import com.example.fc_plaza_service.domain.api.RestaurantServicePort;
import com.example.fc_plaza_service.domain.api.UserServicePort;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import com.example.fc_plaza_service.domain.usecase.RestaurantUseCase;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter.RestaurantPersistenceAdapter;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.RestaurantEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import com.example.fc_plaza_service.infrastructure.adapters.user_service.UserServiceAdapter;
import com.example.fc_plaza_service.infrastructure.adapters.user_service.feign.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
  private final RestaurantEntityMapper userEntityMapper;
  private final RestaurantRepository userRepository;
  private final UserFeignClient userFeignClient;

  @Bean
  public RestaurantPersistencePort restaurantPersistencePort() {
    return new RestaurantPersistenceAdapter(userEntityMapper, userRepository);
  }

  @Bean
  public UserServicePort userServicePort() {
    return new UserServiceAdapter(userFeignClient);
  }

  @Bean
  public RestaurantServicePort restaurantServicePort(
      RestaurantPersistencePort restaurantPersistencePort, UserServicePort userServicePort) {
    return new RestaurantUseCase(restaurantPersistencePort, userServicePort);
  }
}
