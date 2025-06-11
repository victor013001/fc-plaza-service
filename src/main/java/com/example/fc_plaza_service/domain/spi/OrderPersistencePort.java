package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.Order;

public interface OrderPersistencePort {
  void placeOrder(Order order);
}
