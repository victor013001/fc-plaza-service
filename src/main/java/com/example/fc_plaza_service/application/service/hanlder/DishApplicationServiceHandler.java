package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.DishMapper;
import com.example.fc_plaza_service.application.service.DishApplicationService;
import com.example.fc_plaza_service.domain.api.DishServicePort;
import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishUpdateRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.DishResponse;
import java.util.List;
import java.util.Objects;
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

  @Override
  public void updateDish(Long restaurantId, Long dishId, DishUpdateRequest dishUpdateRequest) {
    dishService.updateDish(dishMapper.toModel(dishUpdateRequest, restaurantId), dishId);
  }

  @Override
  public void updateStatusDish(Long restaurantId, Long dishId, ProductStatus status) {
    if (Objects.isNull(status)) {
      throw new BadRequest();
    }
    dishService.updateStatus(restaurantId, dishId, status);
  }

  @Override
  public List<DishResponse> getRestaurantMenu(Long restaurantId, Integer page, Integer size) {
    return dishService.getMenu(restaurantId, page, size).stream()
        .map(dishMapper::toResponse)
        .toList();
  }
}
