package com.example.fc_plaza_service.application.service;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;

public interface OrderApplicationService {
  void createOrder(Long restaurantId, OrderRequest orderRequest);
}
