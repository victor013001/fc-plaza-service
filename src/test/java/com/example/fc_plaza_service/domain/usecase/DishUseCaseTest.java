package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.DishCategoryData.getDishCategory;
import static com.example.fc_plaza_service.util.data.DishData.getValidDish;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.api.UserServicePort;
import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.spi.DishPersistencePort;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

  @InjectMocks private DishUseCase dishUseCase;

  @Mock private DishPersistencePort dishPersistencePort;
  @Mock private RestaurantPersistencePort restaurantPersistencePort;
  @Mock private UserServicePort userServicePort;

  @Test
  void saveDish_success() {
    var dish = getValidDish();

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(true);
    when(dishPersistencePort.existsByName(dish.name(), dish.restaurantId())).thenReturn(false);
    when(restaurantPersistencePort.getLandlordId(dish.restaurantId())).thenReturn(1L);
    when(userServicePort.doesLandlordBelongToEmail(1L)).thenReturn(true);

    dishUseCase.saveDish(dish);

    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verify(dishPersistencePort).existsByName(dish.name(), dish.restaurantId());
    verify(restaurantPersistencePort).getLandlordId(dish.restaurantId());
    verify(userServicePort).doesLandlordBelongToEmail(1L);
    verify(dishPersistencePort).saveDish(dish);
  }

  @Test
  void saveDish_invalidRestaurantId() {
    var dish = getValidDish();

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(false);

    assertThrows(BadRequest.class, () -> dishUseCase.saveDish(dish));

    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verifyNoInteractions(dishPersistencePort, userServicePort);
  }

  @Test
  void saveDish_nameAlreadyExists() {
    var dish = getValidDish();

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(true);
    when(dishPersistencePort.existsByName(dish.name(), dish.restaurantId())).thenReturn(true);

    assertThrows(BadRequest.class, () -> dishUseCase.saveDish(dish));

    verify(dishPersistencePort).existsByName(dish.name(), dish.restaurantId());
    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verifyNoMoreInteractions(dishPersistencePort, restaurantPersistencePort, userServicePort);
  }

  @Test
  void saveDish_landlordDoesNotMatch() {
    var dish = getValidDish();

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(true);
    when(dishPersistencePort.existsByName(dish.name(), dish.restaurantId())).thenReturn(false);
    when(restaurantPersistencePort.getLandlordId(dish.restaurantId())).thenReturn(1L);
    when(userServicePort.doesLandlordBelongToEmail(1L)).thenReturn(false);

    assertThrows(BadRequest.class, () -> dishUseCase.saveDish(dish));

    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verify(dishPersistencePort).existsByName(dish.name(), dish.restaurantId());
    verify(restaurantPersistencePort).getLandlordId(dish.restaurantId());
    verify(userServicePort).doesLandlordBelongToEmail(1L);
    verify(dishPersistencePort, never()).saveDish(any());
  }

  @Test
  void updateDish_success() {
    var dish = getValidDish();
    Long dishId = 10L;

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(true);
    when(dishPersistencePort.existsById(dishId, dish.restaurantId())).thenReturn(true);
    when(restaurantPersistencePort.getLandlordId(dish.restaurantId())).thenReturn(1L);
    when(userServicePort.doesLandlordBelongToEmail(1L)).thenReturn(true);

    dishUseCase.updateDish(dish, dishId);

    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verify(dishPersistencePort).existsById(dishId, dish.restaurantId());
    verify(restaurantPersistencePort).getLandlordId(dish.restaurantId());
    verify(userServicePort).doesLandlordBelongToEmail(1L);
    verify(dishPersistencePort).updateDish(dish, dishId);
  }

  @Test
  void updateDish_invalidDishId() {
    var dish = getValidDish();
    Long dishId = 999L;

    when(restaurantPersistencePort.existsById(dish.restaurantId())).thenReturn(true);
    when(dishPersistencePort.existsById(dishId, dish.restaurantId())).thenReturn(false);

    assertThrows(BadRequest.class, () -> dishUseCase.updateDish(dish, dishId));

    verify(restaurantPersistencePort).existsById(dish.restaurantId());
    verify(dishPersistencePort).existsById(dishId, dish.restaurantId());
    verifyNoMoreInteractions(dishPersistencePort, userServicePort, restaurantPersistencePort);
  }

  @Test
  void updateStatus_success() {
    Long restaurantId = 1L;
    Long dishId = 2L;
    ProductStatus status = ProductStatus.DISABLED;

    when(restaurantPersistencePort.existsById(restaurantId)).thenReturn(true);
    when(dishPersistencePort.existsById(dishId, restaurantId)).thenReturn(true);
    when(restaurantPersistencePort.getLandlordId(restaurantId)).thenReturn(10L);
    when(userServicePort.doesLandlordBelongToEmail(10L)).thenReturn(true);

    dishUseCase.updateStatus(restaurantId, dishId, status);

    verify(restaurantPersistencePort).existsById(restaurantId);
    verify(dishPersistencePort).existsById(dishId, restaurantId);
    verify(restaurantPersistencePort).getLandlordId(restaurantId);
    verify(userServicePort).doesLandlordBelongToEmail(10L);
    verify(dishPersistencePort).updateActive(dishId, status.isActive());
  }

  @Test
  void getMenu_success() {
    Long restaurantId = 1L;
    int page = 0;
    int size = 10;
    var menu = List.of(getDishCategory());

    when(restaurantPersistencePort.existsById(restaurantId)).thenReturn(true);
    when(dishPersistencePort.getMenu(anyLong(), anyInt(), anyInt())).thenReturn(menu);

    var result = dishUseCase.getMenu(restaurantId, page, size);

    assertEquals(menu, result);
    verify(restaurantPersistencePort).existsById(restaurantId);
    verify(dishPersistencePort).getMenu(restaurantId, page, size);
  }
}
