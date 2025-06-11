package com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishId;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDishEntityMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", source = "order")
  @Mapping(target = "dish", source = "dish")
  @Mapping(target = "quantity", source = "quantity")
  OrderDishEntity toEntity(OrderEntity order, DishEntity dish, Integer quantity);

  @AfterMapping
  default void setCompositeId(
      @MappingTarget OrderDishEntity.OrderDishEntityBuilder orderDishEntityBuilder,
      OrderEntity order,
      DishEntity dish) {
    orderDishEntityBuilder.id(new OrderDishId(order.getId(), dish.getId()));
  }
}
