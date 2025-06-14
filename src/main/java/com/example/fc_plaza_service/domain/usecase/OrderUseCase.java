package com.example.fc_plaza_service.domain.usecase;

import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.MsgServicePort;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderUseCase implements OrderServicePort {

  private final OrderPersistencePort orderPersistencePort;
  private final UserServicePort userServicePort;
  private final MsgServicePort msgServicePort;

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
  public void changeStatus(Long orderId, OrderStatus status, Long currentUserId, Integer pin) {
    validOrderChef(orderId, currentUserId);
    validOrderStatusAndPin(orderId, status, pin);
    orderPersistencePort.changeStatus(orderId, status);
    if (OrderStatus.READY.equals(status)) sendOrderPin(orderId);
  }

  private void sendOrderPin(Long orderId) {
    msgServicePort.sendMessage(orderId, orderPersistencePort.getOrderUser(orderId));
  }

  private void validOrderStatusAndPin(Long orderId, OrderStatus status, Integer pin) {
    OrderStatus currentOrderStatus = orderPersistencePort.getOrderStatus(orderId);
    if (OrderStatus.IN_PREPARATION.equals(currentOrderStatus)
        && OrderStatus.DELIVERED.equals(status)) {
      throw new BadRequest();
    }
    if (Objects.nonNull(pin)
        && OrderStatus.READY.equals(currentOrderStatus)
        && OrderStatus.DELIVERED.equals(status)) {
      validPin(orderId, pin);
    }
  }

  private void validPin(Long orderId, Integer pin) {
    if (!msgServicePort.isValidPin(orderId, pin)) {
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
