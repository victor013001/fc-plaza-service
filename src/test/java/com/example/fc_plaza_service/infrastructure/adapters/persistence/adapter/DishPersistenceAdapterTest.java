package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_plaza_service.util.data.DishCategoryData.getDishCategory;
import static com.example.fc_plaza_service.util.data.DishData.getValidDish;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.CategoryEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishProjection;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.DishEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.DishEntityMapperImpl;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.CategoryRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.DishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DishPersistenceAdapterTest {

  @InjectMocks private DishPersistenceAdapter dishPersistenceAdapter;

  @Spy private DishEntityMapper dishEntityMapper = new DishEntityMapperImpl();

  @Mock private DishRepository dishRepository;

  @Mock private RestaurantRepository restaurantRepository;

  @Mock private CategoryRepository categoryRepository;

  @Mock private DishProjection dishProjection;

  @Test
  void saveDish_ShouldMapAndSaveEntity() {
    var dish = getValidDish();
    var restaurant = RestaurantEntity.builder().id(dish.restaurantId()).build();
    var category = CategoryEntity.builder().id(dish.categoryId()).build();

    when(restaurantRepository.getReferenceById(dish.restaurantId())).thenReturn(restaurant);
    when(categoryRepository.getReferenceById(dish.categoryId())).thenReturn(category);
    when(dishRepository.save(any(DishEntity.class))).thenReturn(new DishEntity());

    dishPersistenceAdapter.saveDish(dish);

    verify(dishEntityMapper).toEntity(dish);
    verify(restaurantRepository).getReferenceById(dish.restaurantId());
    verify(categoryRepository).getReferenceById(dish.categoryId());
    verify(dishRepository).save(any(DishEntity.class));
  }

  @Test
  void existsByName_ShouldReturnTrue() {
    String name = "Pizza";
    Long restaurantId = 1L;

    when(dishRepository.existsByNameAndRestaurantId(name, restaurantId)).thenReturn(true);

    boolean result = dishPersistenceAdapter.existsByName(name, restaurantId);

    assertTrue(result);
    verify(dishRepository).existsByNameAndRestaurantId(name, restaurantId);
  }

  @Test
  void existsById_ShouldReturnFalse() {
    Long dishId = 1L;
    Long restaurantId = 2L;

    when(dishRepository.existsByIdAndRestaurantId(dishId, restaurantId)).thenReturn(false);

    boolean result = dishPersistenceAdapter.existsById(dishId, restaurantId);

    assertFalse(result);
    verify(dishRepository).existsByIdAndRestaurantId(dishId, restaurantId);
  }

  @Test
  void updateDish_ShouldCallRepositoryUpdate() {
    var dish = getValidDish();
    Long dishId = 10L;

    dishPersistenceAdapter.updateDish(dish, dishId);

    verify(dishRepository).updateDish(dishId, dish.price(), dish.description());
  }

  @Test
  void updateActive_ShouldCallRepositoryUpdateActiveById() {
    Long dishId = 1L;
    boolean active = false;

    dishPersistenceAdapter.updateActive(dishId, active);

    verify(dishRepository).updateActiveById(dishId, active);
  }

  @Test
  void getMenu_ShouldReturnMappedDishCategoryList() {
    Long restaurantId = 1L;
    int page = 0;
    int size = 2;
    var projections = List.of(dishProjection);

    when(dishRepository.findByRestaurantIdAndActiveTrueOrderByCategoryId(
            eq(restaurantId), any(PageRequest.class)))
        .thenReturn(projections);

    var dishModel = getDishCategory();
    doReturn(dishModel).when(dishEntityMapper).toModel(dishProjection);

    var result = dishPersistenceAdapter.getMenu(restaurantId, page, size);

    assertEquals(1, result.size());
    assertEquals(dishModel, result.get(0));
    verify(dishRepository)
        .findByRestaurantIdAndActiveTrueOrderByCategoryId(eq(restaurantId), any(PageRequest.class));
    verify(dishEntityMapper).toModel(dishProjection);
  }
}
