package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.Order;
import java.util.List;

public interface OrderPersistencePort {
  void placeOrder(Order order);

  boolean allOrdersInDelivered(Long clientId);

  List<Order> getOrders(Integer page, Integer size, String sortedBy, Long restaurantId);

  boolean orderBelongsToRestaurant(Long orderId, Long restaurantId);

  void setChefId(Long orderId, Long currentUserId);

  boolean orderInPending(Long orderId);
}
