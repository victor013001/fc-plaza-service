package com.example.fc_plaza_service.application.service.hanlder;

import com.example.fc_plaza_service.application.mapper.OrderMapper;
import com.example.fc_plaza_service.application.service.OrderApplicationService;
import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
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

  private Long getCurrentUserId() {
    return userServicePort.getCurrentUserId();
  }
}
