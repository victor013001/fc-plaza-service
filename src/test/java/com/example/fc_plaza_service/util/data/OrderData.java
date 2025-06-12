package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.model.OrderDish;
import java.time.LocalDate;
import java.util.List;

public class OrderData {
  private OrderData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static Order getValidOrder() {
    return new Order(1L, 1L, LocalDate.now(), OrderStatus.PENDING, 1L, 1L, List.of(getOrderDish()));
  }

  public static OrderDish getOrderDish() {
    return new OrderDish(1L, "Dish", 2);
  }
}
