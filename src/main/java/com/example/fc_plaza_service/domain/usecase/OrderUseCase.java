package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderUseCase implements OrderServicePort {

  private final OrderPersistencePort orderPersistencePort;

  @Override
  public void placeOrder(Order order) {
    validOrders(order.clientId());
    orderPersistencePort.placeOrder(order);
  }

  @Override
  public List<Order> getOrders(Integer page, Integer size, String sortedBy, Long currentUserId) {
    return orderPersistencePort.getOrders(page, size, sortedBy, currentUserId);
  }

  private void validOrders(Long clientId) {
    if (!orderPersistencePort.allOrdersInDelivered(clientId)) {
      throw new BadRequest();
    }
  }
}
