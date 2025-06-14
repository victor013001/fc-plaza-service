package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.OrderMapper;
import com.example.fc_plaza_service.application.service.OrderApplicationService;
import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderApplicationServiceHandler implements OrderApplicationService {

  private final OrderMapper orderMapper;
  private final OrderServicePort orderServicePort;
  private final UserServicePort userServicePort;

  @Override
  public void createOrder(Long restaurantId, OrderRequest orderRequest) {
    orderServicePort.placeOrder(
        orderMapper.toModel(restaurantId, getCurrentUserId(), orderRequest));
  }

  @Override
  public List<OrderResponse> getOrders(Integer page, Integer size, String sortedBy) {
    return orderServicePort.getOrders(page, size, sortedBy, getCurrentUserId()).stream()
        .map(orderMapper::toResponse)
        .toList();
  }

  @Override
  public void updateOrder(Long orderId, OrderStatus status, Integer pin) {
    if (OrderStatus.IN_PREPARATION.equals(status)) {
      orderServicePort.assignOrderToChef(
          orderId, getCurrentUserId(), getCurrentEmployeeRestaurantId());
    } else {
      orderServicePort.changeStatus(orderId, status, getCurrentUserId(), pin);
    }
  }

  @Override
  public void cancelOrder(Long orderId) {
    orderServicePort.cancelOrder(orderId, getCurrentUserId());
  }

  private Long getCurrentUserId() {
    return userServicePort.getCurrentUserId();
  }

  private Long getCurrentEmployeeRestaurantId() {
    return userServicePort.getCurrentUserRestaurant();
  }
}
