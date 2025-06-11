package com.example.fc_plaza_service.domain.api;

import com.example.fc_plaza_service.domain.model.Order;

public interface OrderServicePort {
  void placeOrder(Order order);
}
