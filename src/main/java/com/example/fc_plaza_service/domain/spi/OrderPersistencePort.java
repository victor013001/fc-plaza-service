package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.model.Order;
import java.util.List;

public interface OrderPersistencePort {
  void placeOrder(Order order);

  boolean allOrdersInDelivered(Long clientId);

  List<Order> getOrders(Integer page, Integer size, String sortedBy, Long restaurantId);

  boolean orderBelongsToRestaurant(Long orderId, Long restaurantId);

  void setChefId(Long orderId, Long currentUserId);

  boolean orderInPending(Long orderId);

  boolean isOrderChef(Long orderId, Long currentUserId);

  void changeStatus(Long orderId, OrderStatus status);

  OrderStatus getOrderStatus(Long orderId);

  Long getOrderUser(Long orderId);

  boolean isOrderClient(Long orderId, Long currentUserId);
}
