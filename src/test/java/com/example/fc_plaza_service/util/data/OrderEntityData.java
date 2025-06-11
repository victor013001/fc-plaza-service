package com.example.fc_plaza_service.util.data;

import static com.example.fc_plaza_service.util.data.RestaurantEntityData.getRestaurantEntity;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishId;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import java.util.concurrent.ThreadLocalRandom;

public class OrderEntityData {
  private OrderEntityData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static OrderEntity getOrderEntity() {
    RestaurantEntity restaurant = getRestaurantEntity();
    Long orderId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);

    return OrderEntity.builder().id(orderId).restaurant(restaurant).build();
  }

  public static OrderDishEntity getOrderDishEntity(OrderEntity order, DishEntity dish) {
    return OrderDishEntity.builder()
        .id(new OrderDishId(order.getId(), dish.getId()))
        .order(order)
        .dish(dish)
        .build();
  }
}
