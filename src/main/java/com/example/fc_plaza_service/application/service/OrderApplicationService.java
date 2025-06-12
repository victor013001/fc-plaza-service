package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.OrderResponse;
import java.util.List;

public interface OrderApplicationService {
  void createOrder(Long restaurantId, OrderRequest orderRequest);

  List<OrderResponse> getOrders(Integer page, Integer size, String sortedBy);

  void assignOrder(Long orderId);
}
