package com.example.fc_plaza_service.util.data;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class RestaurantEntityData {
  private RestaurantEntityData() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static RestaurantEntity getRestaurantEntity() {
    var restaurantId = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    var nit = "NIT-" + restaurantId;
    String phone =
        String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000L, 10000000000000L));
    return RestaurantEntity.builder()
        .id(restaurantId)
        .name("Pasta Place")
        .nit(nit)
        .address("123 Main St")
        .phone(phone)
        .logoUrl("https://example.com/logo.png")
        .userId(1L)
        .build();
  }

  public static List<RestaurantEntity> getRestaurantEntities() {
    return List.of(getRestaurantEntity(), getRestaurantEntity(), getRestaurantEntity());
  }

  public static Page<RestaurantEntity> getRestaurantEntitiesPage() {
    return new PageImpl<>(getRestaurantEntities());
  }
}
