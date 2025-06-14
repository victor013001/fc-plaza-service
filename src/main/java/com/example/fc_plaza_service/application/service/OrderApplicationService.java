package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.OrderResponse;
import java.util.List;

public interface OrderApplicationService {
  void createOrder(Long restaurantId, OrderRequest orderRequest);

  List<OrderResponse> getOrders(Integer page, Integer size, String sortedBy);

  void updateOrder(Long orderId, OrderStatus status, Integer pin);

  void cancelOrder(Long orderId);
}
