package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_plaza_service.domain.model.Restaurant;
import com.example.fc_plaza_service.domain.spi.RestaurantPersistencePort;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.RestaurantEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements RestaurantPersistencePort {
  private static final String LOG_PREFIX = "[RESTAURANT_PERSISTENCE_ADAPTER] >>> ";

  private final RestaurantEntityMapper restaurantEntityMapper;
  private final RestaurantRepository restaurantRepository;

  @Override
  @Transactional
  public void saveRestaurant(Restaurant restaurant) {
    log.info("{} Saving restaurant with nit: {}.", LOG_PREFIX, restaurant.nit());
    restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByNIT(String nit) {
    log.info("{} Checking if nit: {} exists.", LOG_PREFIX, nit);
    return restaurantRepository.existsByNit(nit);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByPhone(String phone) {
    log.info("{} Checking if phone: {} exists.", LOG_PREFIX, phone);
    return restaurantRepository.existsByPhone(phone);
  }

  @Override
  public boolean existsById(Long restaurantId) {
    log.info("{} Checking if restaurant with id: {} exists.", LOG_PREFIX, restaurantId);
    return restaurantRepository.existsById(restaurantId);
  }
}
