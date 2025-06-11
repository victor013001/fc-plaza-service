package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderDishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
import java.util.Collections;
import java.util.List;

public class OrderRequestData {
  private OrderRequestData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static OrderRequest getValidOrderRequest() {
    return new OrderRequest(List.of(getOrderDishRequest()));
  }

  public static OrderDishRequest getOrderDishRequest() {
    return new OrderDishRequest(1L, 2);
  }

  public static OrderRequest getInvalidOrderRequest() {
    return new OrderRequest(Collections.emptyList());
  }
}
