package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.RestaurantMapper;
import com.example.fc_plaza_service.application.service.RestaurantApplicationService;
import com.example.fc_plaza_service.domain.api.RestaurantServicePort;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.RestaurantRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.RestaurantResponse;
import java.util.List;
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

  @Override
  public List<RestaurantResponse> getRestaurants(Integer page, Integer size, String direction) {
    return restaurantService.getRestaurants(page, size, direction).stream()
        .map(restaurantMapper::toResponse)
        .toList();
  }
}
