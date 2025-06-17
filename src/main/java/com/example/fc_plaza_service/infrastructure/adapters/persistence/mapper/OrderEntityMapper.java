package com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import java.time.LocalDateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderEntityMapper {
  @Mapping(target = "id", ignore = true)
  OrderEntity toEntity(Order order, RestaurantEntity restaurant);

  Order toModel(OrderEntity orderEntity);

  @AfterMapping
  default void setDateNow(@MappingTarget OrderEntity.OrderEntityBuilder orderEntityBuilder) {
    orderEntityBuilder.date(LocalDateTime.now());
  }

  @Mapping(target = "dishes", ignore = true)
  Order toModelNoDished(OrderEntity orderEntity);
}
