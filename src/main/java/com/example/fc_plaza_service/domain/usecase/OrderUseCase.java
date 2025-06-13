package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderUseCase implements OrderServicePort {

  private final OrderPersistencePort orderPersistencePort;
  private final UserServicePort userServicePort;

  @Override
  public void placeOrder(Order order) {
    validOrders(order.clientId());
    orderPersistencePort.placeOrder(order);
  }

  @Override
  public List<Order> getOrders(Integer page, Integer size, String sortedBy, Long currentUserId) {
    return orderPersistencePort.getOrders(
        page, size, sortedBy, userServicePort.getCurrentUserRestaurant());
  }

  @Override
  public void assignOrderToChef(Long orderId, Long currentUserId, Long restaurantId) {
    validEmployeeRestaurantId(orderId, restaurantId);
    validOrder(orderId);
    orderPersistencePort.setChefId(orderId, currentUserId);
  }

  @Override
  public void changeStatus(Long orderId, OrderStatus status, Long currentUserId) {
    validOrderChef(orderId, currentUserId);
    validOrderStatus(orderId, status);
    orderPersistencePort.changeStatus(orderId, status);
  }

  private void validOrderStatus(Long orderId, OrderStatus status) {
    OrderStatus currentOrderStatus = orderPersistencePort.getOrderStatus(orderId);
    if (OrderStatus.IN_PREPARATION.equals(currentOrderStatus)
        && OrderStatus.DELIVERED.equals(status)) {
      throw new BadRequest();
    }
  }

  private void validOrderChef(Long orderId, Long currentUserId) {
    if (!orderPersistencePort.isOrderChef(orderId, currentUserId)) {
      throw new BadRequest();
    }
  }

  private void validOrder(Long orderId) {
    if (!orderPersistencePort.orderInPending(orderId)) {
      throw new BadRequest();
    }
  }

  private void validEmployeeRestaurantId(Long orderId, Long restaurantId) {
    if (!orderPersistencePort.orderBelongsToRestaurant(orderId, restaurantId)) {
      throw new BadRequest();
    }
  }

  private void validOrders(Long clientId) {
    if (!orderPersistencePort.allOrdersInDelivered(clientId)) {
      throw new BadRequest();
    }
  }
}
