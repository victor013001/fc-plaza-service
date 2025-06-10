package com.example.fc_plaza_service.application.service.hanlder;

import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishRequest;
import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishUpdateRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import com.example.fc_plaza_service.application.mapper.DishMapper;
import com.example.fc_plaza_service.application.mapper.DishMapperImpl;
import com.example.fc_plaza_service.domain.api.DishServicePort;
import com.example.fc_plaza_service.domain.model.Dish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishApplicationServiceHandlerTest {

  @InjectMocks private DishApplicationServiceHandler dishApplicationServiceHandler;

  @Spy private DishMapper dishMapper = new DishMapperImpl();

  @Mock private DishServicePort dishService;

  @Test
  void createDish_shouldMapAndSaveDish() {
    Long restaurantId = 1L;
    var request = getValidDishRequest();

    dishApplicationServiceHandler.createDish(restaurantId, request);

    verify(dishMapper).toModel(restaurantId, request);
    verify(dishService).saveDish(any(Dish.class));
  }

  @Test
  void updateDish_shouldMapAndUpdateDish() {
    Long restaurantId = 1L;
    Long dishId = 10L;
    var updateRequest = getValidDishUpdateRequest();

    dishApplicationServiceHandler.updateDish(restaurantId, dishId, updateRequest);

    verify(dishMapper).toModel(updateRequest, restaurantId);
    verify(dishService).updateDish(any(Dish.class), eq(dishId));
  }
}
