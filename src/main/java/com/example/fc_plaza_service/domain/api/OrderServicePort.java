package com.example.fc_plaza_service.domain.api;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.model.Order;
import java.util.List;

public interface OrderServicePort {
  void placeOrder(Order order);

  List<Order> getOrders(Integer page, Integer size, String sortedBy, Long currentUserId);

  void assignOrderToChef(Long orderId, Long currentUserId, Long restaurantId);

  void changeStatus(Long orderId, OrderStatus status, Long currentUserId);
}
