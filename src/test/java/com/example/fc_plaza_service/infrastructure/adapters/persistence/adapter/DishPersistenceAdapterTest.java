package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_plaza_service.util.data.DishData.getValidDish;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.CategoryEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.DishEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.DishEntityMapperImpl;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.CategoryRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.DishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishPersistenceAdapterTest {

  @InjectMocks private DishPersistenceAdapter dishPersistenceAdapter;

  @Spy private DishEntityMapper dishEntityMapper = new DishEntityMapperImpl();

  @Mock private DishRepository dishRepository;

  @Mock private RestaurantRepository restaurantRepository;

  @Mock private CategoryRepository categoryRepository;

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
}
