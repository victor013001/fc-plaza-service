package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.OrderDishResponse;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.OrderResponse;
import java.time.LocalDate;
import java.util.List;

public class OrderResponseData {
  private OrderResponseData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static OrderResponse getValidOrderResponse() {
    return new OrderResponse(
        1L, 1L, LocalDate.now(), OrderStatus.PENDING, 1L, 1L, List.of(getOrderDishResponse()));
  }

  public static OrderDishResponse getOrderDishResponse() {
    return new OrderDishResponse(1L, "Dish", 2);
  }
}
