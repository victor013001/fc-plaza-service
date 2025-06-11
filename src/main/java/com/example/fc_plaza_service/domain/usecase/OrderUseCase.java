package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderUseCase implements OrderServicePort {

  private final OrderPersistencePort orderPersistencePort;

  @Override
  public void placeOrder(Order order) {
    orderPersistencePort.placeOrder(order);
  }
}
