package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.DishMapper;
import com.example.fc_plaza_service.application.service.DishApplicationService;
import com.example.fc_plaza_service.domain.api.DishServicePort;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishApplicationServiceHandler implements DishApplicationService {

  private final DishMapper dishMapper;
  private final DishServicePort dishService;

  @Override
  public void createDish(Long restaurantId, DishRequest dishRequest) {
    dishService.saveDish(dishMapper.toModel(restaurantId, dishRequest));
  }
}
