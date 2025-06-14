package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

  @Override
  @Transactional(readOnly = true)
  public boolean allOrdersInDelivered(Long clientId) {
    log.info("{} Checking if all orders are delivered for client id: {}.", LOG_PREFIX, clientId);
    return orderRepository.noOrdersWithStatusDifferentFromDelivered(clientId);
  }

  @Override
  public List<Order> getOrders(Integer page, Integer size, String sortedBy, Long restaurantId) {
    log.info(
        "{} Getting order in status: {} for restaurant: {}.", LOG_PREFIX, sortedBy, restaurantId);
    OrderStatus status = OrderStatus.valueOf(sortedBy);
    return orderRepository
        .findAllByRestaurantIdAndStatus(restaurantId, status, buildPageRequest(page, size))
        .stream()
        .map(orderEntityMapper::toModel)
        .toList();
  }

  @Override
  public boolean orderBelongsToRestaurant(Long orderId, Long restaurantId) {
    return orderRepository.existsByIdAndRestaurantId(orderId, restaurantId);
  }

  @Override
  public void setChefId(Long orderId, Long currentUserId) {
    orderRepository.assignChefAndSetInPreparation(orderId, currentUserId);
  }

  @Override
  public boolean orderInPending(Long orderId) {
    return orderRepository.isOrderPending(orderId);
  }

  @Override
  public boolean isOrderChef(Long orderId, Long currentUserId) {
    return orderRepository.existsByIdAndChefId(orderId, currentUserId);
  }

  @Override
  public void changeStatus(Long orderId, OrderStatus status) {}

  @Override
  public OrderStatus getOrderStatus(Long orderId) {
    return orderRepository.getStatusById(orderId);
  }

  @Override
  public Long getOrderUser(Long orderId) {
    return orderRepository.getClientIdById(orderId);
  }

  @Override
  public boolean isOrderClient(Long orderId, Long currentUserId) {
    return orderRepository.existsByIdAndClientId(orderId, currentUserId);
  }

  private PageRequest buildPageRequest(Integer page, Integer size) {
    return PageRequest.of(page, size, Sort.by("date").descending());
  }
}
