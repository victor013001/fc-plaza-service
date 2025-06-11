package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderDishEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.DishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.OrderDishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.OrderRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {
  private static final String LOG_PREFIX = "[ORDER_PERSISTENCE_ADAPTER] >>> ";

  private final OrderEntityMapper orderEntityMapper;
  private final OrderDishEntityMapper orderDishEntityMapper;
  private final OrderRepository orderRepository;
  private final OrderDishRepository orderDishRepository;
  private final DishRepository dishRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  @Transactional
  public void placeOrder(Order order) {
    log.info("{} Saving order for restaurant: {}.", LOG_PREFIX, order.restaurantId());

    RestaurantEntity restaurant = restaurantRepository.getReferenceById(order.restaurantId());
    OrderEntity orderEntity = orderRepository.save(orderEntityMapper.toEntity(order, restaurant));

    order
        .dishes()
        .forEach(
            orderDish -> {
              DishEntity dish = dishRepository.getReferenceById(orderDish.dishId());
              OrderDishEntity orderDishEntity =
                  orderDishEntityMapper.toEntity(orderEntity, dish, orderDish.quantity());
              orderDishRepository.save(orderDishEntity);
            });
  }
}
