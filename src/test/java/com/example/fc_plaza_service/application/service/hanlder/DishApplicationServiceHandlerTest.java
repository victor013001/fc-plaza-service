package com.example.fc_plaza_service.application.service.hanlder;

import static com.example.fc_plaza_service.util.data.DishCategoryData.getDishCategory;
import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishRequest;
import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishUpdateRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.application.mapper.DishMapper;
import com.example.fc_plaza_service.application.mapper.DishMapperImpl;
import com.example.fc_plaza_service.domain.api.DishServicePort;
import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Dish;
import com.example.fc_plaza_service.domain.model.DishCategory;
import java.util.List;
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

  @Test
  void updateStatusDish_shouldCallServiceWithValidStatus() {
    Long restaurantId = 1L;
    Long dishId = 10L;
    ProductStatus status = ProductStatus.ENABLED;

    dishApplicationServiceHandler.updateStatusDish(restaurantId, dishId, status);

    verify(dishService).updateStatus(restaurantId, dishId, status);
  }

  @Test
  void updateStatusDish_shouldThrowBadRequestWhenStatusIsNull() {
    Long restaurantId = 1L;
    Long dishId = 10L;

    assertThrows(
        BadRequest.class,
        () -> dishApplicationServiceHandler.updateStatusDish(restaurantId, dishId, null));

    verifyNoInteractions(dishService);
  }

  @Test
  void getRestaurantMenu_shouldReturnMappedDishResponses() {
    Long restaurantId = 1L;
    Integer page = 0;
    Integer size = 5;

    var dishCategories = List.of(getDishCategory());
    when(dishService.getMenu(restaurantId, page, size)).thenReturn(dishCategories);

    var result = dishApplicationServiceHandler.getRestaurantMenu(restaurantId, page, size);

    assertEquals(dishCategories.size(), result.size());
    verify(dishService).getMenu(restaurantId, page, size);
    verify(dishMapper, times(dishCategories.size())).toResponse(any(DishCategory.class));
  }
}
