package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.RestaurantMapper;
import com.example.fc_plaza_service.application.service.RestaurantApplicationService;
import com.example.fc_plaza_service.domain.api.RestaurantServicePort;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.RestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantApplicationServiceHandler implements RestaurantApplicationService {

  private final RestaurantMapper restaurantMapper;
  private final RestaurantServicePort restaurantService;

  @Override
  public void createRestaurant(RestaurantRequest restaurantRequest) {
    restaurantService.saveRestaurant(restaurantMapper.toModel(restaurantRequest));
  }
}
