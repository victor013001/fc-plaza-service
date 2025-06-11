package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_plaza_service.domain.model.Dish;
import com.example.fc_plaza_service.domain.model.DishCategory;
import com.example.fc_plaza_service.domain.spi.DishPersistencePort;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.DishEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.CategoryRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.DishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class DishPersistenceAdapter implements DishPersistencePort {
  private static final String LOG_PREFIX = "[DISH_PERSISTENCE_ADAPTER] >>> ";

  private final DishEntityMapper dishEntityMapper;
  private final DishRepository dishRepository;
  private final RestaurantRepository restaurantRepository;
  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public void saveDish(Dish dish) {
    log.info("{} Saving dish with name: {}.", LOG_PREFIX, dish.name());
    DishEntity dishEntity = dishEntityMapper.toEntity(dish);
    dishEntity.setRestaurant(restaurantRepository.getReferenceById(dish.restaurantId()));
    dishEntity.setCategory(categoryRepository.getReferenceById(dish.categoryId()));
    dishRepository.save(dishEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByName(String name, Long restaurantId) {
    log.info("{} Checking if dish: {} exists in restaurant: {}.", LOG_PREFIX, name, restaurantId);
    return dishRepository.existsByNameAndRestaurantId(name, restaurantId);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsById(Long dishId, Long restaurantId) {
    log.info("{} Checking if dish with id: {} exists.", LOG_PREFIX, dishId);
    return dishRepository.existsByIdAndRestaurantId(dishId, restaurantId);
  }

  @Override
  @Transactional
  public void updateDish(Dish dish, Long dishId) {
    log.info("{} Updating dish with id: {}.", LOG_PREFIX, dishId);
    dishRepository.updateDish(dishId, dish.price(), dish.description());
  }

  @Override
  @Transactional
  public void updateActive(Long dishId, boolean active) {
    log.info("{} Updating dish: {} active status to: {}", LOG_PREFIX, dishId, active);
    dishRepository.updateActiveById(dishId, active);
  }

  @Override
  public List<DishCategory> getMenu(Long restaurantId, Integer page, Integer size) {
    log.info("{} Getting restaurant: {} menu, sorted by category.", LOG_PREFIX, restaurantId);
    return dishRepository
        .findByRestaurantIdAndActiveTrueOrderByCategoryId(
            restaurantId, buildPageRequest(page, size))
        .stream()
        .map(dishEntityMapper::toModel)
        .toList();
  }

  private PageRequest buildPageRequest(Integer page, Integer size) {
    return PageRequest.of(page, size);
  }
}
